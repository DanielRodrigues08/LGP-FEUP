/* eslint-disable vue/multi-word-component-names */
import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import PrimeVue from "primevue/config";
import Sidebar from "primevue/sidebar"
import Button from "primevue/button"
import Avatar from "primevue/avatar"
import Stepper from "primevue/stepper"
import StepperPanel from 'primevue/stepperpanel';
import Menu from 'primevue/menu';
import "primeflex/themes/primeone-light.css"
import "primevue/resources/themes/aura-light-blue/theme.css"
import '@mdi/font/css/materialdesignicons.css'

// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

import 'primevue/resources/primevue.min.css';
import ConfirmationService from 'primevue/confirmationservice';

import App from './App.vue'
import router from './router'
import { useAuthStore } from '@/stores/auth';

const vuetify = createVuetify({
    theme: {
        defaultTheme: 'light'
    },
    components,
    directives,
})

const app = createApp(App)

app.use(ConfirmationService);
app.use(createPinia())
app.use(router)
app.use(vuetify)

const authStore = useAuthStore();
authStore.loadStoredUser();

app.use(PrimeVue)
app.component('Sidebar', Sidebar);
app.component('Button', Button);
app.component('Stepper', Stepper);
app.component('StepperPanel', StepperPanel);
app.component('Avatar', Avatar);
app.component('Menu', Menu);


app.mount('#app')
