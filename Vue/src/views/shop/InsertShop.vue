<script>
import config from "@/class/config"
export default{
  name:"ShopInsert",
  computed: {
    config() {
      return config
    }
  },
  data(){
    return{
      product:{
        name: "",
        price: "",
        value: "",
        description: "",
        image: "default.jpg"
      }
    }
  },
  methods:{
    handleSubmit(){
      // 检查price参数是否合法
      if(!/^[0-9]+(.[0-9]{1,2})?$/.test(this.product.price)){
        this.$message.error("商品价格参数不合法");
        return;
      }
      // 检查所有参数不为“”
      if(this.product.name===""||this.product.price===""||this.product.value===""||this.product.description===""){
        this.$message.error("请填写所有必填项");
        return;
      }
      this.$axios.post(config.baseUrl+config.apiUrl+config.api.insertShop,{
        name:this.product.name,
        price:this.product.price,
        value:this.product.value,
        description:this.product.description,
        image:this.product.image,
      },{
        headers:{
          'token':this.$cookies.get("token")
        }
      }).then(res=>{
        if(res.data.Code===200){
          this.$message.success(res.data.Response);
          this.$router.push("/dashboard");
       }else{
          this.$message.error(res.data.Response);
        }
      })
    },
    beforeAvatarUpload(file){
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    handleAvatarSuccess(res) {
      if(res.Code === 200){
        this.product.image = res.base64;
        this.$message.success(res.Response);
      }else{
        this.$message.error(res.Response);
      }
    }
  }
}
</script>

<template>
  <h2 style="text-align: center">当前正在新增商品</h2>
  <el-form label-width="100px" :model="product">
    <el-form-item label="商品图片">
      <el-upload
        class="avatar-uploader"
        :action="config.baseUrl+config.apiUrl+config.api.uploadImg"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload"
      >
        <img :src="product.image" class="avatar">
      </el-upload>
    </el-form-item>
    <el-form-item label="商品名称">
      <el-input v-model="product.name" placeholder="请输入商品名称"></el-input>
    </el-form-item>
    <el-form-item label="商品价格">
      <el-input  v-model.trim="product.price" placeholder="请输入商品价格"></el-input>
    </el-form-item>
    <el-form-item label="商品库存">
      <el-input v-model.number="product.value" placeholder="请输入商品库存"></el-input>
    </el-form-item>
    <el-form-item label="商品描述">
      <el-input v-model="product.description" type="textarea" placeholder="请输入商品描述"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="handleSubmit">提交</el-button>
    </el-form-item>
  </el-form>
</template>

<style scoped>
/* 可以在这里添加样式 */
.el-form {
  max-width: 600px;
  margin: 0 auto;
}
.avatar{
  max-width: 500px;
}
</style>
