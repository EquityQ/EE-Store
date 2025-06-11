<template>
  <div class="payment-dashboard">
    <el-row :gutter="20">
      <!-- 账户余额卡片 -->
      <el-col :span="12">
        <el-card shadow="hover" class="balance-card">
          <div class="card-header">
            <span>账户余额</span>
          </div>
          <div class="card-content">
            <h1>¥ {{ mount }}</h1>
            <el-button type="primary" @click="handleRecharge">充值</el-button>
            <el-button type="success" @click="handleTransfer">转账</el-button>
          </div>
        </el-card>
      </el-col>

      <!-- 交易记录卡片 -->
      <el-col :span="12">
        <el-card shadow="hover" class="transaction-card">
          <template #header>
            <div class="card-header">
              <span>最近交易记录</span>
            </div>
          </template>
          <div class="card-content based-card">
            <el-table :data="transactions" style="width: 100%">
              <el-table-column prop="orderId" label="订单哈希" width="290" />
              <el-table-column prop="time" label="日期" width="200" />
              <el-table-column prop="value" label="交易发生额" width="140" />
              <el-table-column prop="other" label="备注" width="150" />
            </el-table>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import config from "@/class/config";
export default {
  data() {
    return {
      mount:"1000.00",
      transactions: []
    };
  },
  methods: {
    handleRecharge() {
      var token = this.$cookies.get("token");
      this.$prompt('请输入充值金额', '充值', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /^[0-9]*\.?[0-9]+$/,
        inputErrorMessage: '请输入正确的金额'
      }).then(({ value }) => {
        this.$axios.post(config.baseUrl+config.apiUrl+config.api.payInsert+"?value="+value,{
        },{headers:{
          'token':token
          }}).then(res=>{
           if(res.data.Code===200){
             this.$message.success(res.data.Response);
             this.$router.go(0);
           }else{
             this.$message.error(res.data.Response);
           }
          })
      }).catch(
          () => {
            this.$message({
              type: 'info',
              message: '用户取消充值'
            });
          }
      )
    },
handleTransfer() {
  const token = this.$cookies.get("token");

  this.$prompt('请输入转账金额', '转账', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    inputPattern: /^[0-9]*\.?[0-9]+$/,
    inputErrorMessage: '请输入正确的金额'
  }).then(({ value: amount }) => {
    this.$prompt('请输入收款方账号', '转账', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      inputPattern: /.+/,
      inputErrorMessage: '请输入正确的账号'
    }).then(({ value: account }) => {
      this.transfer(amount, account, token);
    }).catch(() => {
      this.showCancelMessage();
    });
  }).catch(() => {
    this.showCancelMessage();
  });
},

transfer(amount, account, token) {
  this.$axios.post(config.baseUrl + config.apiUrl + config.api.payEx, {}, {
    params: {
      value: amount,
      towho: account
    },
    headers: {
      'token': token
    }
  }).then(res => {
    if (res.data.Code === 200) {
      this.$message.success(res.data.Response);
      this.$router.go(0);
    } else {
      this.$message.error(res.data.Response);
    }
  }).catch(() => {
    this.$message({
      type: 'info',
      message: '用户取消转账'
    });
  });
},

showCancelMessage() {
  this.$message({
    type: 'info',
    message: '用户取消转账'
  });
}

  },
  mounted() {
    var token = this.$cookies.get("token");
    if(token==null||token===""){
      this.$message.error("未检测到登录态");
      this.$cookies.remove("token");
      this.$router.push("/user/login");
      return;
    }
  //   监测token有效
    this.$axios.post(config.baseUrl+config.apiUrl+config.api.authToken,{},{headers:{
      'token':token
      }}).then(res=>{
        if(res.data.Code==200){
        //   注册token并更新
          this.$cookies.set("token",token,{expires:30,path:"/"});
          this.$cookies.set("permission",res.data.permission,{path:"/"});
        }else{
          this.$message.error(res.data.Response);
          this.$cookies.remove("token");
          this.$router.push("/user/login");
          return;
        }
    })

    this.$axios.post(config.baseUrl+config.apiUrl+config.api.getOrders,{},{headers:{
      'token':token
      }}).then(res=>{
        if(res.data.Code==200){
          this.transactions = res.data.Orders;
           // this.transactions.value 保留两位小数 同时验证如果如果不为负数 则前面添加+好同时标注好人民币单位
          for (var i=0;i<this.transactions.length;i++){
            if(this.transactions[i].value>=0){
              this.transactions[i].value = "￥+"+this.transactions[i].value.toFixed(2);
            }else{
              this.transactions[i].value = "￥"+this.transactions[i].value.toFixed(2);
            }
          }
        }else{
          this.$message.error(res.data.Response);
        }
    })
    this.$axios.post(config.baseUrl+config.apiUrl+config.api.payMyinfo,{},{headers:{
      'token':token
      }}).then(res=>{
        if(res.data.Code===200){
          // res.data.Value;转换为保留两位小数
          this.mount = res.data.Value.toFixed(2);
        }else{
          this.$message.error(res.data.Response);
        }
    })
  }
};
</script>

<style scoped>
.payment-dashboard {
  padding: 20px;
}

.balance-card, .function-card, .transaction-card {
  height: 100%;
}

.card-header {
  font-size: 18px;
  font-weight: bold;
  padding: 10px 0;
  border-bottom: 1px solid #ebebeb;
}

.card-content  {
  text-align: center;
}

.card-content h1 {
  margin: 20px 0;
  font-size: 24px;
}

.el-button {
  margin: 10px;
}
.based-card{
  text-align: left !important;
}
</style>
