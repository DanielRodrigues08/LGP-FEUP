// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
import HomeView from '../views/HomeView.vue';
import UserView from '../views/UserView.vue';
import OnboardeesView from '@/views/OnboardeesView.vue';
import NewOnboardee from '@/views/NewOnboardee.vue';
import Login from '@/views/Login.vue';
import AdminArea from '@/views/AdminArea.vue';
import NewUser from '@/views/NewUser.vue';
import Statistics from '@/views/Statistics.vue';
import NotFound from '@/views/NotFound.vue';
import OnboardeeProfileView from '@/views/OnboardeeProfileView.vue';
import ForbiddenView from '@/views/ForbiddenView.vue';
import ProcessesView from '@/views/ProcessesView.vue';
import JobSchedulingView from '@/views/JobSchedulingView.vue';


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', name: 'statistics', component: Statistics },
    { path: '/user/:id', name: 'user', component: UserView, props: true },
    { path: '/onboardees', name: 'onboardees', component: OnboardeesView },
    { path: '/add-onboardee', name: 'add-onboardee', component: NewOnboardee },
    { path: '/onboardees/:id', name: 'OnboardeeProfile', component: OnboardeeProfileView },
    { path: '/login', name: 'login', component: Login },
    { path: '/admin-area', name: 'admin-area', component: AdminArea },
    { path: '/add-user', name: 'add-user', component: NewUser },
    { path: '/:pathMatch(.*)*', name: 'not-found', component: NotFound },
    { path: '/forbidden', name: 'forbidden', component: ForbiddenView },
    { path: '/processes', name: 'processes', component: ProcessesView },
    { path: '/job-scheduling', name: 'job-scheduling', component: JobSchedulingView },
  ]
});

router.beforeEach(async (to, from, next) => {
  const authStore = useAuthStore();

  if (!authStore.user && authStore.token) {
    try {
      await authStore.loadStoredUser();
    } catch (error) {
      console.error('Error loading stored user:', error);
    }
  }

  // Redirect to login if the user is not authenticated and not already on the login page
  if (!authStore.user && to.name !== 'login') {
    next({ name: 'login' });
  } else if (authStore.user) {
    if (to.name === 'admin-area' && !authStore.user.roles.includes('ADMIN')) {
      next({ name: 'forbidden' });
    } else {
      next();
    }
  } else {
    next();
  }
});


export default router;
