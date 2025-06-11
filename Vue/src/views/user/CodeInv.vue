<script>
import { ElMessage } from 'element-plus';
import config from "@/class/config"
export default {
  name: 'AuthoriZation',
  data() {
    return {
      authorizationCode: ''
    };
  },
  methods: {
    submitAuthorizationCode() {
      if (this.authorizationCode === '') {
        ElMessage.error('请输入提权码');
        return;
      }
      // 判断code是否包含中文非法字符等
      if (/[^\u4e00-\u9fa5a-zA-Z0-9]/.test(this.authorizationCode)) {
        ElMessage.error('提权码中不能包含中文或其他非法字符');
        return;
      }

      var token = this.$cookies.get("token");
      if(token==null||token===""){
        ElMessage.error('未检测到登录态');
        this.$cookies.remove("token");
        this.$router.push("/user/login");
        return;
      }
      var permission = this.$cookies.get("permission");
      if(permission==="admin"){
        ElMessage.error('您已拥有管理员权限，无需再次申请');
        return;
      }
      this.$axios.post(config.baseUrl+config.apiUrl+config.api.code,{},{
        headers:{
          'token':this.$cookies.get("token"),
          "code":this.authorizationCode
        }
      }).then(res=>{
        this.$message.success(res.data.Response);
      //   刷新页面
      //   等待一秒
        this.$message.success("正在将数据更新至DashBoard");
        setTimeout(() => {
          this.$router.push("/dashboard");
        }, 1000);
      })
    }
}
}
</script>

<template>
  <div class="auth-container">
    <div class="auth-form">
      <h2>输入提权码</h2>
      <el-input
        v-model="authorizationCode"
        placeholder="默认提权码为EquityQ"
        clearable
      ></el-input>
      <el-button type="primary" @click="submitAuthorizationCode">提交</el-button>
    </div>
  </div>
</template>

<style scoped>
.auth-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f0f2f5;
}

.auth-form {
  background-color: #ffffff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  width: 300px;
  text-align: center;
}

h2 {
  margin-bottom: 20px;
  font-size: 18px;
  font-weight: bold;
}

.el-input {
  margin-bottom: 20px;
}

.el-button {
  width: 100%;
}
</style>
