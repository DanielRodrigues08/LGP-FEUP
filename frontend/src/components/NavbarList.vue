<script setup>
import Sidebar from 'primevue/sidebar';
import ListIcon from './icons/ListIcon.vue'
import { ref } from "vue";

const visible = ref(false);
</script>

<template>
    <Sidebar v-model:visible="visible" class="sidebar w-16rem">
          <template #header>
              <div class="flex primary-color align-items-center gap-2">
                  <Avatar image="https://www.seaprodexhanoi.com.vn/img/no_avatar.jpg" shape="circle" />
                  <span class="font-bold">Name Surname</span>
              </div>
          </template>

          <p>TODO</p>
    </Sidebar>
    <Button text rounded @click="visible = true" class="ml-2 mr-2 menu-button">
        <ListIcon/>
    </Button>
</template>

<style scoped>
    .sidebar {
        justify-content: center;
        align-items: center;
    }

    .menu-button {
        padding: 0;
        justify-content: center;
        height: calc(0.8 * var(--navbar-height));
        aspect-ratio: 1 / 1;
    }
</style>