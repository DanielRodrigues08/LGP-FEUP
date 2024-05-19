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

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', name: 'home', component: HomeView },
    { path: '/user/:id', name: 'user', component: UserView, props: true },
    { path: '/onboardees', name: 'onboardees', component: OnboardeesView },
    { path: '/add-onboardee', name: 'add-onboardee', component: NewOnboardee },
    { path: '/:pathMatch(.*)*', name: 'not-found', component: NotFound },
    { path: '/onboardees/:id', name: 'OnboardeeProfile', component: OnboardeeProfileView },
    { path: '/login', name: 'login', component: Login },
    { path: '/admin-area', name: 'admin-area', component: AdminArea },
    { path: '/add-user', name: 'add-user', component: NewUser },
    { path: '/statistics', name: 'statistics', component: Statistics }
  ]
});

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore();
  if (!authStore.user && authStore.token) {
    authStore.loadStoredUser();
  }
  if (to.name !== 'login' && !authStore.user) {
    next({ name: 'login' });
  } else {
    console.log(authStore.user.roles)
    if (to.name === 'admin-area' && (!authStore.user || !authStore.user.roles.includes('ADMIN'))) {
      next({ name: 'statistics' });
    } else {
      next();
    }
  }
});

export default router;
