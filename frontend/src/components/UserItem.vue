<template>
  <div>
    <h1>User Information</h1>
    <div v-if="user">
      <p>Username: {{ user.username }}</p>
      <p>Role: {{ user.role }}</p>
    </div>
    <div v-else>
      <p>No user found.</p>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { useAuthStore } from '@/stores/auth';
import { ref, onMounted } from "vue";

export default {
  setup() {
    const authStore = useAuthStore();
    const user = ref(null);
    const userId = ref(null);

    const fetchUser = async (userId) => {
      try {
        const response = await axios.get(`${import.meta.env.VITE_API_URL}/users/${userId}`, { headers: authStore.authData() });
        user.value = response.data;
      } catch (error) {
        console.error('Error fetching user:', error);
      }
    };

    onMounted(() => {
      userId.value = this.$route.params.id; // Assuming the route parameter is named 'id'
      fetchUser(userId.value);
    });

    return {
      user
    };
  }
};
</script>


<style scoped>
/* Add styles as needed */
</style>

