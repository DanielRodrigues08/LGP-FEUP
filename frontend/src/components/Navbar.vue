<script>
import ToggleButton from 'primevue/togglebutton';
import { RouterLink } from 'vue-router';
import LifecycleIcon from './icons/LifecycleIcon.vue';
import Sidebar from './Sidebar.vue';
import NavbarProfile from './NavbarProfile.vue';
import axios from 'axios';

export default {
  components: {
    ToggleButton,
    RouterLink,
    LifecycleIcon,
    Sidebar,
    NavbarProfile
  },
  data() {
    return {
      checked: false
    };
  },
  methods: {
    async handleToggle() {
      try {
        if (this.checked) {
          await axios.post('http://localhost:8081/hrmembers/1/attendance/mark') //done for user 1 for now... change when login done
        } else {
          await axios.post('http://localhost:8081/hrmembers/1/leave/mark')
        }
      } catch (error) {
        console.error('Error:', error);
      }
    }
  }
};
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
    <ToggleButton v-model="checked" onLabel="Present" offLabel="Not present" onIcon="pi pi-briefcase" 
        offIcon="pi pi-ban" class="w-9rem" @change="handleToggle"/>
    <NavbarProfile/>

  </div>
</template>

<style scoped>
  .navbar-menu {
    display: flex;
    flex-grow: 1; /* Allow the navbar-menu to grow and push the profile and toggle button to the edges */
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

