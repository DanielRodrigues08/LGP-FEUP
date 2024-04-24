<!-- eslint-disable vue/multi-word-component-names -->
<script setup>
import { RouterLink } from 'vue-router'
import LifecycleIcon from './icons/LifecycleIcon.vue'
import Sidebar from './Sidebar.vue'
import NavbarProfile from './NavbarProfile.vue'
</script>

<template>
  <div class="navbar">
    <div class="navbar-menu">
      <Sidebar/>
      <nav>
        <RouterLink to="/">
          <div class="navbar-menu">
            <LifecycleIcon class="ml-2 logo"/>
          </div>
        </RouterLink>
      </nav>
    </div>
    <NavbarProfile/>
  </div>
</template>

<style scoped>
  .navbar-menu {
    display: flex;
    flex-direction: row;
    align-items: center;
  }

  .navbar {
    --navbar-height: 3.25rem;
    display: flex;
    flex-direction: row;
    width: 100%;
    height: var(--navbar-height);
    justify-content: space-between;
    align-items: center;
    box-shadow: 0px 2px 6px rgba(0, 0, 0, 0.5);
  }

  .logo {
    display: flex;
    max-height: calc(0.7 * var(--navbar-height));
    aspect-ratio: 1 / 1;
  }
</style>
