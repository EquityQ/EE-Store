<template>
  <div class="login-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>用户登录</span>
        </div>
      </template>
      <el-form :model="form" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input type="password" v-model="form.password" show-password></el-input>
        </el-form-item>
        <el-form-item label="验证码">
          <el-row :gutter="20">
            <el-col :span="14">
              <el-input v-model="form.captcha"></el-input>
            </el-col>
            <el-col :span="10">
              <el-button type="primary" @click="sendCaptcha">获取验证码</el-button>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="Captcha：" v-if="showCaptcha">
          <img :src="captchaImg" alt="captcha">
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">登录</el-button>
          <el-button @click="register">注册</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import config from "@/class/config.js"
import {MD5} from "crypto-js"
export default {
  data() {
    return {
      form: {
        username: '',
        password: '',
        captcha: ''
      },
      captchaImg:"",
      showCaptcha:false
    };
  },
  methods: {
    salt(){
      return {
        username:this.form.username,
        captcha:this.form.captcha,
        password:MD5(this.form.password).toString()
      }
    },
    onSubmit() {
      //   发送请求带参数给后端，后端返回数据，判断是否登录成功，成功则跳转页面，失败则提示错误信息
      var datas = this.salt();
      if(datas.name==""|| datas.password==""|| datas.captcha=="" || this.showCaptcha == false){
        this.$message.error("请输入完整信息");
        return;
      }
      this.$axios.post(config.baseUrl+config.apiUrl+config.api.login,datas).then(res=>{
        if(res.data.Code==200){
          this.$cookies.set("token",res.data.token,{expires:30,path:"/"});
          this.$cookies.set("permission",res.data.permission,{path:"/"});
          this.$message.success(res.data.Reason);
          this.$router.push("/");
        }else{
          this.$message.error(res.data.Reason);
          this.form.captcha = "";
          this.form.password = "";
          this.form.username = "";
          this.sendCaptcha();
        }
      })
    },
    sendCaptcha() {
      const time = new Date().getTime();
      this.captchaImg = config.baseUrl+config.apiUrl+config.api.captcha+`?time=${time}`;
      this.showCaptcha = true;
    },
    register() {
      this.$router.push("/user/register");
    }
  },
  mounted() {
    var token = this.$cookies.get("token");
    if(token==null|| token==""){
      return;
    }
    this.$axios.post(config.baseUrl+config.apiUrl+config.api.authToken,{},{
      headers:{
      'token':token
      }}).then(res=>{
      if(res.data.Code===200){
        // 更新token有效期
        this.$cookies.set("token",token,{expires:30,path:"/"});
        this.$cookies.set("permission",res.data.permission,{path:"/"});
        this.$router.push("/");
      }else{
        // 删除token
        this.$cookies.remove("token");
        this.$message.error(res.data.Reason);
      }
    })
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.box-card {
  width: 400px;
}

.card-header {
  text-align: center;
  font-size: 20px;
  font-weight: bold;
}
</style>
