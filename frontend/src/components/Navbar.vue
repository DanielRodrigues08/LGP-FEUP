<!-- eslint-disable vue/multi-word-component-names -->
<script setup>
import { RouterLink } from 'vue-router'
import NextPhaseIcon from './icons/NextPhaseIcon.vue'
import NavbarList from './NavbarList.vue'
import NavbarProfile from './NavbarProfile.vue'
</script>

<template>
  <div class="navbar">
    <div class="navbar-menu">
      <NavbarList/>
      <nav>
        <RouterLink to="/">
          <div class="navbar-menu">
            <NextPhaseIcon class="ml-2 logo"/>
            <div class="logo-text">
              <span class="pl-2 font-bold">LIFE</span>
              <span>CYCLE</span>
            </div>
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

  .logo-text {
    color: #033A65;
    font-size: calc(0.4 * var(--navbar-height));
    /* TODO: add appropriate font for the logo
    font-family: "P22 Speyside W01SC SemiBold SC";  */
  }

  .logo {
    width: calc(0.7 * var(--navbar-height));
    aspect-ratio: 1 / 1;
  }
</style>
