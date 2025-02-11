<template>
  <div class="flex justify-content-center">
    <Button text rounded type="button" @click="toggle" aria-haspopup="true" class="ml-2 mr-2 menu-button" aria-controls="overlay_menu">
      <ProfileIcon class="p-0 navbar-profile"/>
    </Button>
    <Menu v-if="user" ref="profile_menu" class="p-0" :model="profile_items" :popup="true" :header="false">
      <template #submenuheader>
        <button class="relative overflow-hidden w-full p-link flex align-items-center p-2 hover:surface-200 border-noround">
          <Avatar image="https://www.seaprodexhanoi.com.vn/img/no_avatar.jpg" class="mr-2" shape="circle" />
          <div class="text-xs inline-flex flex-column">
            <span class="primary-color font-bold">{{ user.name }}</span>
            <span>{{ user.permissionLevel }}</span>
          </div>
        </button>
      </template>
      <template #item="{ item }">
        <a class="relative overflow-hidden w-full p-link p-2 flex align-items-center pl-2 pr-2 hover:surface-200 border-noround" @click="item.action">
          <span :class="item.icon" class="primary-color"></span>
          <span class="text-color text-xs ml-2">{{ item.label }}</span>
        </a>
      </template>
    </Menu>
    <Menu v-else ref="profile_menu" class="p-0" :model="profile_items_logged_out" :popup="true" :header="false">
      <template #submenuheader>
        <button class="relative overflow-hidden w-full p-link flex align-items-center p-2 hover:surface-200 border-noround">
          <div class="text-xs text-color flex-column">
            <span>User not logged in.</span>
          </div>
        </button>
      </template>
      <template #item="{ item }">
        <a class="relative overflow-hidden w-full p-link p-2 flex align-items-center pl-2 pr-2 hover:surface-200 border-noround" @click="item.action">
          <span :class="item.icon" class="primary-color"></span>
          <span class="text-color text-xs ml-2">{{ item.label }}</span>
        </a>
      </template>
    </Menu>
  </div>

  <Dialog :id="dialog" v-model="showProfileDialog" :visible="showProfileDialog" modal header="Edit Profile" @hide="showProfileDialog = false" :closable="false" :draggable="false">
    <span class="p-text-secondary block mb-5">Update your information.</span>
    <div v-if="user" class="parent">
      <div class="flex align-items-center gap-3 mb-3">
        <label for="name" class="font-semibold w-6rem">Name</label>
        <InputText id="name" v-model="user.name" class="flex-auto" autocomplete="off" />
      </div>
      <div class="flex align-items-center gap-3 mb-3">
        <label for="email" class="font-semibold w-6rem">Email</label>
        <InputText id="email" v-model="user.email" class="flex-auto" autocomplete="off" />
      </div>
      <div class="flex align-items-center gap-3 mb-5">
        <label for="password" class="font-semibold w-6rem">Password</label>
        <InputText id="password" v-model="newPassword" type="password" class="flex-auto" autocomplete="off" placeholder="New Password" />
      </div>
    </div>
    <div v-else>
      <p>User not found</p>
    </div>
    <div class="flex justify-content-end gap-2">
      <Button type="button" label="Cancel" severity="secondary" @click="cancelEdit"></Button>
      <Button type="button" label="Save" @click="saveChanges"></Button>
    </div>
  </Dialog>
</template>


<script>
import { ref, onMounted, watch } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import Dialog from 'primevue/dialog';
import ProfileIcon from './icons/ProfileIcon.vue';
import InputText from 'primevue/inputtext';
import Button from 'primevue/button';
import Menu from 'primevue/menu';
import Avatar from 'primevue/avatar';
import { useAuthStore } from '@/stores/auth';

export default {
  components: {
    Dialog,
    ProfileIcon,
    InputText,
    Button,
    Menu,
    Avatar,
  },
  setup() {
    const showProfileDialog = ref(false);
    const user = ref(null);
    const newPassword = ref('');
    const authStore = useAuthStore();
    const router = useRouter();
    
    const cancelEdit = () => {
      showProfileDialog.value = false;
      newPassword.value = ''; // Reset the password field when cancelling the edit
    };

    const fetchAuthenticatedUser = async () => {
      try {
        if (authStore.user) {
          const response = await axios.get(`${import.meta.env.VITE_API_URL}/auth`, { headers: authStore.authData() });
          user.value = response.data;
        }
      } catch (error) {
        console.error('Error fetching user:', error);
      }
    };

    const saveChanges = async () => {
      try {
        const updateData = { ...user.value };
        if (newPassword.value) {
          updateData.password = newPassword.value;
        }
        await axios.put(`${import.meta.env.VITE_API_URL}/users/${user.value.id}`, updateData, { headers: authStore.authData() });
        showProfileDialog.value = false;
      } catch (error) {
        console.error('Error saving changes:', error);
      }
    };

    const toggle = (event) => {
      profile_menu.value.toggle(event);
    };

    const editProfile = () => {
      newPassword.value = ''; // Reset the password field when editing the profile
      showProfileDialog.value = true;
    };

    const logoutUser = () => {
      authStore.logout();
      router.push('/login')
            .then(router.go(0));
    };

    const loginUser = () => {
      router.push("/login");
    };

    const profile_menu = ref();
    const profile_items = ref([
      {
        items: [
          { separator: true },
          { label: 'Edit Profile', icon: 'pi pi-cog', action: editProfile },
          { label: 'Logout', icon: 'pi pi-sign-out', action: logoutUser }
        ]
      }
    ]);
    const profile_items_logged_out = ref([
      {
        items: [
          { label: 'Login', icon: 'pi pi-sign-in', action: loginUser }
        ]
      }
    ]);

    watch(() => authStore.user, (newUser) => {
      if (newUser) {
        fetchAuthenticatedUser();
      }
    });

    onMounted(() => {
      authStore.loadStoredUser(); // Load stored user information
      if (authStore.user) {
        fetchAuthenticatedUser();
      }
    });

    return {
      showProfileDialog,
      toggle,
      profile_menu,
      profile_items,
      profile_items_logged_out,
      user,
      newPassword,
      saveChanges,
      cancelEdit,
    };
  },
};
</script>

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

.p-dialog .p-dialog-content {
  width: 25rem;
}

#dialog .p-dialog-title {
  background-color: var(--teal-500) !important;
  color: #ce2929;
}

#dialog .p-dialog-titlebar-close{
  display: none;
}
</style>
