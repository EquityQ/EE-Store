<template>
  <header class="topper">
    <ElRow type="flex" justify="center" align="middle">
      <ElCol :span="24" class="topper-content">
        <ElRow type="flex" justify="space-between" align="middle">
          <ElCol :span="8">
            <div class="topper-links">
              <ElLink href="https://xlog.eioe.fun" class="topper-link">Blog</ElLink>
              <ElLink href="https://github.com/EquityQ/" class="topper-link">Github</ElLink>
            </div>
          </ElCol>
          <ElCol :span="8" class="topper-center">
            <div class="topper-logo">
              <img @click="goToGithub" src="../assets/logo.jpg" alt="Logo" class="topper-logo-img" />
            </div>
          </ElCol>
          <ElCol :span="8">
            <div class="topper-social">
              <ElLink href="#" class="topper-link">Website</ElLink>
              <ElLink href="#" class="topper-link">Bilibili</ElLink>
            </div>
          </ElCol>
        </ElRow>
        <ElRow type="flex" justify="center" align="middle">
          <div class="topper-copyright">
            <br>
            <span>Copyright © 2023-2024 EquityQ. All rights reserved.</span>
          </div>
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
  color: #00ffaf; /* 鼠标悬停时的文字颜色 */
}

.topper-center {
  text-align: center;
}

.topper-logo-img {
  width: 100px;
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
