<!-- eslint-disable vue/multi-word-component-names -->
<script setup>
import Sidebar from 'primevue/sidebar';
import ListIcon from './icons/ListIcon.vue'
import LifecycleIcon from './icons/LifecycleIcon.vue'
import { RouterLink } from 'vue-router'
import { ref } from "vue";
import { useAuthStore } from '@/stores/auth';

const visible = ref(false);
  
const boards = ref([
  {
    name: "Statistics",
    route: "/",
    icon: "pi pi-chart-bar" 
  },
  {
    name: "Onboardees",
    route: "/onboardees",
    icon: "pi pi-address-book" 
  },
  {
    name: "Processes",
    route: "/processes",
    icon: "pi pi-file"
  },
  {
    name: "Admin area",
    route: "/admin-area",
    icon: "pi pi-wrench"
  }
]);


// Function to check if the "Admin area" link should be shown
const shouldShowBoard = (board) => {
    const authStore = useAuthStore();
    if(authStore.user){
        return authStore.user.roles.includes('ADMIN') || board.name !== 'Admin area';
    }
  };

</script>

<template>
    <Sidebar v-model:visible="visible" class="sidebar w-16rem">
      <template #header>
        <div class="sidebar-logo">
          <LifecycleIcon class="logo mr-2" />
        </div>
      </template>
      <ul class="list-none p-0 mt-3 overflow-hidden">
        <div v-for="(board, index) in boards" v-bind:key="index">
          <RouterLink v-if="shouldShowBoard(board)" :to="board.route" class="p-0">
            <li>
              <a v-ripple class="boards flex align-items-center cursor-pointer p-3 text-700 hover:surface-100 transition-duration-150 transition-colors p-ripple">
                <i :class="board.icon" class="mr-2"></i>
                <span class="font-semibold">{{ board.name }}</span>
              </a>
            </li>
          </RouterLink>
        </div>
      </ul>
    </Sidebar>
    <Button text rounded @click="visible = true" class="ml-2 mr-2 menu-button">
      <ListIcon />
    </Button>
</template>
  
  <style>
  .p-sidebar .p-sidebar-content {
    padding: 0;
  }
  </style>
  
  <style scoped>
  .sidebar {
    justify-content: center;
    align-items: center;
  }
  
  .sidebar-logo {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
  }
  
  .menu-button {
    padding: 0;
    justify-content: center;
    height: calc(0.8 * var(--navbar-height));
    aspect-ratio: 1 / 1;
  }
  
  .logo {
    --navbar-height: 3.25rem;
    display: flex;
    justify-content: center;
    height: calc(0.6 * var(--navbar-height));
    width: 100%;
    aspect-ratio: 1 / 1;
  }
  
  .boards:hover {
    border-right: 4px solid #033A65;
  }
  </style>
  