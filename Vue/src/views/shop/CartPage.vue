<template>
  <div>
    <el-card class="box-card" style="margin-top: 20px">
      <template #header>
        <div class="card-header">
          <span>你的购物车</span>
        </div>
      </template>
      <el-table :data="cart" style="width: 100%">
        <el-table-column prop="name" label="商品名称" width="180" />
        <el-table-column prop="price" label="单价" width="180" />
        <el-table-column prop="quantity" label="数量" width="180" />
        <el-table-column prop="localtotal" label="小计" width="180" />
        <el-table-column label="操作">
          <template #default="scope">
            <el-button size="medium" @click="removeFromCart(scope.row)">删除</el-button>
            <el-input-number style="margin-left: 20px" v-model="scope.row.quantity" :min="1" :max="100" label="数量" @change="updateQuantity(scope.row)" />
          </template>
        </el-table-column>
      </el-table>
      <div class="total">
        <strong>总价: ￥{{ total }}</strong>
      </div>
      <br>
      <div style="display: flex;justify-content: right">
        <el-button type="primary" style="min-width: 120px;" @click="payForItem">提交订单</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
import config from "@/class/config";
export default {
  data() {
    return {
      cart: [],
      total: 0
    }
  },
  mounted() {
    var token = this.$cookies.get("token");
    if(token==null&&token==""){
      this.$message.error("未检测到登录态,无法使用购物车功能。");
      this.$cookies.remove("token");
      this.$router.push("/user/login");
      return;
    }
  //   扫描所有cookie 除了token 与permission
    this.$axios.get(config.baseUrl+config.apiUrl+config.api.ShopInfo,{}).then(res=>{
      if(res.data.Code===200){
        for (var i=0;i<res.data.elements.length;i++){
          if(this.$cookies.get(res.data.elements[i].name)!=null){
            this.cart.push({ name: res.data.elements[i].name, price: res.data.elements[i].price, quantity: this.$cookies.get(res.data.elements[i].name), localtotal: res.data.elements[i].price*this.$cookies.get(res.data.elements[i].name),maxValue: res.data.elements[i].value });
            this.total += parseFloat(res.data.elements[i].price*this.$cookies.get(res.data.elements[i].name));
          }
        }
      }else{
        this.$message.error(res.data.Response);
      }
    })

  },
  methods: {
    payForItem(){
      this.$router.push("/order");
    },
    removeFromCart(item) {
      var token = this.$cookies.get("token");
      if(token==null&&token==""){
        this.$message.error("未检测到登录态,无法使用购物车功能。");
        this.$cookies.remove("token");
        this.$router.push("/user/login");
        return;
      }
      this.$cookies.remove(item.name);
    //   刷新页面
      this.$router.go(0);
    },
    updateQuantity(item) {
      var token = this.$cookies.get("token");
      if(token==null&&token==""){
        this.$message.error("未检测到登录态,无法使用购物车功能。");
        this.$cookies.remove("token");
        this.$router.push("/user/login");
        return;
      }
      if(item.quantity>item.maxValue){
        this.$message.error("商品库存不足,无法继续添加。");
        item.quantity = item.maxValue;
        return;
      }
    //   直接修改cookie
      this.$cookies.set(item.name,item.quantity,{path:"/",expires:30});
    //   更新数据
      var total = 0;
      for (var i=0;i<this.cart.length;i++){
        if(this.cart[i].name===item.name){
          this.cart[i].quantity = item.quantity;
          this.cart[i].localtotal = item.quantity*item.price;
        }
        total += parseFloat(this.cart[i].localtotal);
      }
      this.total = total;
    }
  }
}

</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.total {
  text-align: right;
  margin-top: 10px;
  font-size: 18px;
}
</style>
