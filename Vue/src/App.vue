<template>
  <TopperCop></TopperCop>
  <RouterView></RouterView>
  <FeetCop/>
</template>

<script>

import Components_feet from "./components/FeetCop.vue";
import Componets_top from "./components/TopperCop.vue"
export default {
  name: 'App',
  components: {
    FeetCop:Components_feet,
    TopperCop:Componets_top
  }
}
</script>

<style>
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s ease;
}
.fade-enter-from, .fade-leave-to {
  opacity: 0;
}
body{
  margin: 0;
}
</style>
