<script setup>
import { RouterView } from 'vue-router'
import Navbar from './components/Navbar.vue';
</script>

<template>
  <header>
    <div class="wrapper">
      <Navbar/>
    </div>
  </header>

  <RouterView />
</template>