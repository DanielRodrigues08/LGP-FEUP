<script setup>
import ProfileIcon from './icons/ProfileIcon.vue'
import { ref } from "vue";

const profile_menu = ref();
const profile_items = ref([
  {
    items: [
      {
        separator: true
      },
      {
        label: 'Edit',
        icon: 'pi pi-sign-out'
      },
      {
        label: 'Logout',
        icon: 'pi pi-sign-out'
      }
    ]
  }
]);

const toggle = (event) => {
  profile_menu.value.toggle(event);
};
</script>

<template>
    <div class="flex justify-content-center">
        <Button text rounded type="button" @click="toggle" aria-haspopup="true" class="ml-2 mr-2 menu-button" aria-controls="overlay_menu">
            <ProfileIcon class="p-0 navbar-profile"/>
        </Button>
        <Menu ref="profile_menu" class="p-0" :model="profile_items" :popup="true" :header="false">
            <template #submenuheader>
                <button class="relative overflow-hidden w-full p-link flex align-items-center p-2 hover:surface-200 border-noround">
                    <Avatar image="https://www.seaprodexhanoi.com.vn/img/no_avatar.jpg" class="mr-2" shape="circle" />
                    <div class="text-xs inline-flex flex-column">
                    <span class="primary-color font-bold">Name Surname</span>
                    <span>Admin</span>
                    </div>
                </button>
            </template>
            <template #item="{ item, props }">
                <a class="relative overflow-hidden w-full p-link p-2 flex align-items-center pl-2 pr-2 hover:surface-200 border-noround" v-bind="props.action">
                    <span :class="item.icon" class="primary-color" />
                    <span class="text-color text-xs ml-2">{{ item.label }}</span>
                </a>
            </template>
        </Menu>
    </div>
</template>

<style scoped>
    .primary-color {
      color: #033A65;
    }

    .menu-button {
      padding: 0;
      justify-content: center;
      height: calc(0.8 * var(--navbar-height));
      aspect-ratio: 1 / 1;
    }

    .navbar-profile {
      justify-content: center;
      align-items: center;
      height: calc(0.6 * var(--navbar-height));
      aspect-ratio: 1 / 1;
    }
</style>