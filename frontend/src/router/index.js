import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import UserView from '../views/UserView.vue' // Import the new UserView component

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/user/:id', // Define the path for the user information page with a route parameter for the user ID
      name: 'user',
      component: UserView, // Use the UserView component for this route
      props: true // Pass route params as props to the component
    }
  ]
})

export default router
