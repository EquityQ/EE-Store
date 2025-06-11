<template>
  <div class="store-container">
    <el-card class="box-card">
      <div class="card-header">
        <span >EquityQ's Shop</span>
      </div>
      <el-row class="el_row_class" :gutter="20">
        <el-col :span="8" v-for="(item, index) in goods" style="padding-top: 10px;padding-bottom: 10px" :key="index">
          <el-card class="product-card">
            <img :src="item.image" alt="商品图片" class="product-image" />
            <div class="product-info">
              <p><strong>名称:</strong> {{ item.name }}</p>
              <p><strong>价格:</strong> {{ item.price }} 元</p>
              <p><strong>库存:</strong> {{ item.value }} 件</p>
              <p><strong>描述:</strong> {{ item.description }}</p>
              <el-button type="primary" @click="addToCart(item.name)">加入购物车</el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
import config from "@/class/config";
export default {
  data() {
    return {
      goods: []
    };
  },
  mounted() {
    // 从后端获取商品列表数据
    this.$axios.get(config.baseUrl+config.apiUrl+config.api.ShopInfo).then(response => {
      this.goods = response.data.elements;
    });
  },
  methods: {
    addToCart(name) {
      var token = this.$cookies.get("token");
      if(token==null&&token==""){
        this.$message.error("未检测到登录态,无法使用购物车功能。");
        this.$cookies.remove("token");
        return;
      }
    //   存入cookie
      var value = this.$cookies.get(name);
      // 检索当前库存
      var good_value = 0;
      for (var i=0;i<this.goods.length;i++){
        if(this.goods[i].name===name){
          good_value = this.goods[i].value;
          break;
        }
      }
      if(good_value<=0){
        this.$message.error("商品库存不足。");
        return;
      }
      if(value==null||value===""){
        this.$message.success("新增商品已加入购物车。");
        this.$cookies.set(name,1,{path:"/",expires:30});
      }else{
        if(parseInt(value)>=good_value){
          this.$message.error("商品库存不足,无法加入购物车。");
          return;
        }
        this.$message.success("商品数量已增加进购物车。");
        this.$cookies.set(name,parseInt(value)+1,{path:"/",expires:30});
      }
    }
  }
};
</script>

<style scoped>
.store-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.box-card {
  width: 100%;
  margin-bottom: 20px;
}

.card-header {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 10px;
}

.product-card {
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}

.product-image {
  width: 100%;
  max-height: 300px;
  object-fit: cover;
  margin-bottom: 10px;
}

.product-info {
  padding: 10px;
  font-size: 14px;
  color: #606266;
}
</style>
