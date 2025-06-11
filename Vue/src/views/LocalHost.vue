<template>
<!--  传参admin-->
<!--  居中div 打印欢迎登录，你的权限是-->
  <div class="center" style="text-align: center">
    <br>
    <div class="welcome">欢迎登录：<p style="color: #333333; text-decoration: underline; display: inline;">{{ name }}</p> <a @click="logout" style="cursor: pointer;color: #0063c2">注销</a></div>
    <br>
    <div class="permission">当前登录IP：{{ip}}，权限为{{permission}}。</div>
  </div>
  <datalocal :name="name"></datalocal>
</template>

<script>
import config from "@/class/config"
import datalocal from "@/components/DataLocal.vue"
export default {
  name: 'LocalHost',
  components: {
    datalocal: datalocal
  },
  methods: {
    logout(){
      this.$cookies.remove("permission");
      var token = this.$cookies.get("token");
      this.$axios.post(config.baseUrl+config.apiUrl+config.api.logout,{},{headers:{
        'token':token
        }}).then(res=>{
          if(res.data.Code==200){
            this.$message.success(res.data.Response);
          }else{
            this.$message.error(res.data.Response);
          }
      })
      this.$cookies.remove("token");
      this.$router.push("/user/login");
    }
  },
  data() {
    return {
      name:"",
      checked:false,
      ip:"",
      permission:""
    }
  },
  mounted() {
    if(this.$cookies.get("permission")==="user"){
      this.permission = "普通用户";
    }else{
      this.permission = "超级管理员";
    }
    var token = this.$cookies.get("token");
    if(token==null||token==""){
      this.$message.error("未检测到登录态");
      this.$cookies.remove("token");
      this.$router.push("/user/login");
      return;
    }
    this.$axios.post(config.baseUrl+config.apiUrl+config.api.authToken,{},{headers:{
      'token':token
      }}).then(res=>{
        if(res.data.Code==200){
          // 更新token有效期
          this.$cookies.set("token",token,{expires:30,path:"/"});
          this.$cookies.set("permission",res.data.permission,{path:"/"});
        }else{
          // 删除token
          this.$cookies.remove("token");
          this.$message.error(res.data.Reason);
          this.$router.push("/user/login");
       }
      })
    this.$axios.post(config.baseUrl+config.apiUrl+config.api.getUserInfo,{},{
      headers:{
        'token':token
      }
    }).then(res=>{
      if(res.data.Code==200){
        this.name = res.data.username;
      }else{
        this.$message.error(res.data.Reason);
        this.$cookies.remove("token");
        this.$router.push("/user/login");
      }
    })
    this.$axios.get(config.baseUrl+config.apiUrl+config.api.ip).then(res=>{
      this.ip = res.data.ip;
    })
  },
}
</script>

<style scoped>
.center{
  font-size: 2rem;
}
</style>