<template>
  <header class="topper">
    <ElRow type="flex" justify="center" align="middle">
      <ElCol :span="24" class="topper-content">
        <ElRow type="flex" justify="space-between" align="middle">
          <ElCol :span="1">
            <div class="topper-links">
              <div class="topper-logo">
                <img @click="goToGithub" src="../assets/logo.jpg" alt="Logo" class="topper-logo-img" />
              </div>
            </div>
          </ElCol>
          <ElCol :span="8" class="topper-left">
            FOR EVERYONE'S DREAM.
          </ElCol>
          <ElCol :span="8" class="topper-center">
            <ElLink v-bind:href="'#/dashboard'" class="topper-link">Dashboard</ElLink>
            <ElLink v-bind:href="'/'" class="topper-link">Shop</ElLink>
            <ElLink v-bind:href="'#/cart'" class="topper-link">Cart</ElLink>
            <ElLink v-bind:href="'#/payment'" class="topper-link">Payment</ElLink>
          </ElCol>
        </ElRow>
      </ElCol>
    </ElRow>
  </header>
</template>

<script setup>
import { ElRow, ElCol, ElLink } from 'element-plus';

function goToGithub() {
  window.open('https://github.com/EquityQ/CloudJavaWeb');
}
</script>

<style scoped>
.topper {
  background-color: #000000; /* 黑色背景 */
  color: #ffffff; /* 白色文字 */
  padding: 20px 0;
  top: 0;
  width: 100%;
  z-index: 1000; /* 确保顶部栏在其他内容之上 */
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.5); /* 添加阴影效果 */
}

.topper-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

.topper-links,
.topper-social {
  display: flex;
  justify-content: space-around;
  align-items: center;
  flex-wrap: wrap;
}

.topper-link {
  color: #ffffff;
  text-decoration: none;
  margin: 0 10px;
  font-size: 16px;
  padding-top: 10px;
  transition: color 0.3s ease; /* 平滑过渡效果 */
}

.topper-link:hover {
  color: #00ffc1; /* 鼠标悬停时的文字颜色 */
  text-decoration: none;
}

.topper-center {
  text-align: center;
}

.topper-logo-img {
  width: 60px;
  height: auto;
  border-radius: 50%;
  cursor: pointer;
  transition: transform 0.3s ease; /* 平滑过渡效果 */
}

.topper-logo-img:hover {
  transform: scale(1.1); /* 鼠标悬停时放大图片 */
}

.topper-copyright {
  text-align: center;
  margin-top: 10px;
  font-size: 14px;
}
</style>
