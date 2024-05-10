import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import UserView from '../views/UserView.vue'
import OnboardeesView from '@/views/OnboardeesView.vue'
import NewOnboardee from '@/views/NewOnboardee.vue'
import Login from '@/views/Login.vue'
import AdminArea from '@/views/AdminArea.vue'
import NewUser from '@/views/NewUser.vue'
import NotFound from '@/views/NotFound.vue'

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
      path: '/onboardees',
      name: 'onboardees',
      component: OnboardeesView
    },
    {
      path: '/add-onboardee',
      name: 'add-onboardee',
      component: NewOnboardee
    },
    { 
      path: '/:pathMatch(.*)*', 
      name: 'not-found',
      component: NotFound 
    },
    {
      path: '/onboardee_profile/:id',
      //path: '/onboardee_profile/:id',
      name: 'OnboardeeProfile',
      component: OnboardeeProfileView
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/admin-area',
      name: 'admin-area',
      component: AdminArea
    },
    {
      path: '/add-user',
      name: 'add-user',
      component: NewUser
    }

  ]
})

export default router
