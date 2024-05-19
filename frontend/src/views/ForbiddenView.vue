<template>
  <div class="not-found-container">
    <img src="../assets/404.png" alt="404 Not Found" class="not-found-image">
    <h3 class="not-found-text">You don't have permissions to access this resource</h3>
    <span @click="goBack" class="back-link">Go back to previous page</span>
  </div>
</template>

<script>
export default {
  name: "Forbidden",
  methods: {
      goBack() {
      this.$router.go(-1);
      }
}
};
</script>

<style scoped>
.not-found-container {
  color: #033A65;
  height: calc(100vh - 3.25rem);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.not-found-image {
  width: 25%;
  height: auto;
}

.not-found-text {
  font-size: 2.5em;
  font-weight: bold;
  margin-top: 0.6em;
}

.back-link {
  color: #2e9bff;
  text-decoration: none;
  font-size: 1.2rem;
  }

.back-link:hover {
  text-decoration: underline;
}

.back-link:focus {
  outline: none;
}

</style>
