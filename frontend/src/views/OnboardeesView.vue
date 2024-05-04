<template>
  <div class="onboardees">
    <div class="search-container">
      <div>
        <InputText v-model="globalFilter.value" placeholder="Search for name" @input="filterOnboardees" />
      </div>  
      <router-link to="/add-onboardee">
          <Button class="add-button" label="New onboardee" icon="pi pi-plus" />
      </router-link>
    </div>
    
    <div v-for="(stateOnboardees, state) in filteredOnboardees" :key="state">
      <h3 :class="stateClass(state)">{{ toTitleCase(state) }}  onboardees</h3>
      <DataTable class="custom-table" :value="stateOnboardees" :globalFilter="globalFilter" @filter="filterOnboardees">
        <Column field="id" header="ID"></Column>
        <Column field="name" header="Name"></Column>
        <Column field="email" header="Email"></Column>
        <Column field="phoneNumber" header="Phone"></Column>
        <Column field="startDate" header="Start date"></Column>
      </DataTable>
    </div>
  </div>
</template>

<script>
import DataTable from 'primevue/datatable';
import Column from 'primevue/column'; 
import axios from 'axios';
import Button from 'primevue/button';
import InputText from 'primevue/inputtext';

export default {
  components: {
    DataTable,
    Column,
    Button,
    InputText
  },
  data() {
    return {
      onboardees: [],
      originalOnboardees: [],
      globalFilter: { value: null, matchMode: 'contains', field: 'name' },
      selectedOnboardee: null
    };
  },
  mounted() {
    this.fetchOnboardees();

  },
  computed: {
    filteredOnboardees() {
      const filtered = {};
      for (const onboardee of this.onboardees) {
        if (!filtered[onboardee.state]) {
          filtered[onboardee.state] = [];
        }
        filtered[onboardee.state].push(onboardee);
      }
      return filtered;
    }
  },
  methods: {
    async fetchOnboardees() {
      try {
        const response = await axios.get('http://localhost:8081/onboardees');
        this.onboardees = response.data;
        this.originalOnboardees = response.data;

      } catch (error) {
        console.error('Error fetching onboardees:', error);
      }
    },
    filterOnboardees() {
      this.globalFilter = {
        value: this.globalFilter.value,
        matchMode: 'contains',
        field: 'name'
      };
      if (this.globalFilter.value === '') {
        this.onboardees = this.originalOnboardees;
      } else {
        this.onboardees = this.originalOnboardees.filter(item => item.name.toLowerCase().includes(this.globalFilter.value.toLowerCase()));
      }    
    },
    stateClass(state) {
      return {
        'incoming': state === 'INCOMING',
        'ongoing': state === 'ONGOING',
        'completed': state === 'COMPLETED',
        'aborted': state === 'ABORTED',
      };
    },
    toTitleCase(str) {
      return str.toLowerCase().split(' ').map(word => word.charAt(0).toUpperCase() + word.slice(1)).join(' ');
    }
  }

};
</script>

<style scoped>
  h3 {
    font-weight:500;
    margin-left: 2.1rem;
    margin-top: 1.5rem;
  }
  .incoming {
    color: green;
  }
  .ongoing {
    color: #1976D2;
  }
  .completed {
    color: purple;
  }
  .aborted {
    color: #C81E1E;
  }
  .add-button {
    background-color: #033A65;
    color: white;
    border-color: rgba(0, 0, 0, 0.1); 
  }
  .search-container {
    display: flex;
    align-items: center;
    margin-top: 1.5rem;
    margin-left: 2rem;
  }

  .search-container > div {
    margin-right: 1rem;
  }
  .custom-table {
    max-width: 90%;
    margin: 0 auto;
    margin-top: 0.5em;
    margin-bottom: 2em;

  }
</style>
