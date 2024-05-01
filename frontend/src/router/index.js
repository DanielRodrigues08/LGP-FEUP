import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import UserView from '../views/UserView.vue'
import OnboardeesView from '@/views/OnboardeesView.vue'
import NewOnboardeeView from '@/views/NewOnboardeeView.vue'
import OnboardeeProfileView from '@/views/OnboardeeProfileView.vue'

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
    },
    {
      path: '/onboardees', // Define the path for the onboardees page
      name: 'onboardees',
      component: OnboardeesView // Use the OnboardeesView component for this route
    },
    {
      path: '/add-onboardee', // Define the path for the onboardees page
      name: 'add-onboardee',
      component: NewOnboardeeView // Use the OnboardeesView component for this route
    },
    {
      path: '/onboardee_profile/:id',
      name: 'OnboardeeProfile',
      component: OnboardeeProfileView
    }

  ]
})

export default router
