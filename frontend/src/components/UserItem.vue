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
import { authData } from "@/api/AuthProvider";

export default {
  data() {
    return {
      user: null
    };
  },
  mounted() {
    // Fetch user information based on user ID from URL parameter
    const userId = this.$route.params.id; // Assuming the route parameter is named 'id'
    this.fetchUser(userId);
  },
  methods: {
    fetchUser(userId) {
      // Make an API call to retrieve user information based on user ID
      axios.get(`${import.meta.env.VITE_API_URL}/users/${userId}`, {headers: authData()})
          .then(response => {
            this.user = response.data;
          })
          .catch(error => {
            console.error('Error fetching user:', error);
          });
    }
  }
};
</script>

<style scoped>
/* Add styles as needed */
</style>

