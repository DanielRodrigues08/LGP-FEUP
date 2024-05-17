<script>
import { ref } from "vue";
import Dialog from 'primevue/dialog';
import ProfileIcon from './icons/ProfileIcon.vue';
import InputText from 'primevue/inputtext';
import Button from 'primevue/button';
import Menu from 'primevue/menu';
import Avatar from 'primevue/avatar';
import axios from 'axios';

export default {
  components: {
    Dialog,
    ProfileIcon,
    InputText,
    Button,
    Menu,
    Avatar
  },
  setup() {
    const showProfileDialog = ref(false);
    const user = ref(null);
    const newPassword = ref('');
    const userId = 2;

    const toggle = (event) => {
      profile_menu.value.toggle(event);
    };

    const editProfile = () => {
      showProfileDialog.value = true;
    };

    const profile_menu = ref();
    const profile_items = ref([
      {
        items: [
          {
            separator: true
          },
          {
            label: 'Edit Profile',
            icon: 'pi pi-cog',
            action: editProfile
          },
          {
            label: 'Logout',
            icon: 'pi pi-sign-out'
          }
        ]
      }
    ]);

    const fetchOnboardeeById = async (userId) => {
      try {
        const response = await axios.get(`http://localhost:8081/users/${userId}`);
        user.value = response.data;
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
        await axios.put(`http://localhost:8081/users/${userId}`, updateData);
        showProfileDialog.value = false;
      } catch (error) {
        console.error('Error saving changes:', error);
      }
    };

    const cancelEdit = () => {
      showProfileDialog.value = false;
    };

    fetchOnboardeeById(userId);

    return { showProfileDialog, toggle, profile_menu, profile_items, user, newPassword, saveChanges, cancelEdit };
  }
}
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
          <template #item="{ item }">
              <a class="relative overflow-hidden w-full p-link p-2 flex align-items-center pl-2 pr-2 hover:surface-200 border-noround" @click="item.action()">
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
          <Button type="button" label="Cancel" severity="secondary" @click="showProfileDialog = false"></Button>
          <Button type="button" label="Save" @click="saveChanges"></Button>
      </div>
  </Dialog>
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
