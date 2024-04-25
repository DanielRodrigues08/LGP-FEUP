<script>
    import DataTable from 'primevue/datatable';
    import Column from 'primevue/column';
    import ColumnGroup from 'primevue/columngroup';  
    import Row from 'primevue/row';    
    import axios from 'axios'; // Import axios for making HTTP requests
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
      };
    },
    mounted() {
      this.fetchOnboardees(); // Fetch onboardees data when the component is mounted
    },
    methods: {
      async fetchOnboardees() {
        try {
          const response = await axios.get('http://localhost:8081/api/onboardees');
          console.log(response)
          this.onboardees = response.data; // Update onboardees data with the response
        } catch (error) {
          console.error('Error fetching onboardees:', error);
        }
      },
    },
  };
</script>

<template>
  <div class="onboardees">
    <div class="title-container">
      <h1>Incoming onboardees</h1>
      <router-link to="/add-onboardee">
          <Button label="New onboardee" icon="pi pi-plus" />
      </router-link>
    </div>
    
    <div class="search-container">
      <div>
        <InputText v-model="globalFilter" placeholder="Search..." />
      </div>
    </div>
    
    <DataTable class="custom-table" :value="onboardees" :globalFilter="globalFilter">
      <Column field="id" header="ID"></Column>
      <Column field="name" header="Name"></Column>
      <Column field="email" header="Email"></Column>
      <!-- Add more columns as needed -->
    </DataTable>
  </div>
</template>

<style scoped>
  h1 {
    color: #033A65;
    font-weight:600;
    margin-right: 2rem;
  }
  button {
    background-color: #033A65;
    color: white;
    border-color: rgba(0, 0, 0, 0.1); 
  }
  .title-container {
    display: flex;
    align-items: center;
    margin-top: 1.5rem;
    margin-left: 2rem;
  }

  .search-container {
    display: flex;
    margin-top: 1rem;
    margin-left: 2rem;
  }

  .search-container > div {
    margin-right: 1rem;
  }
  .custom-table {
    max-width: 90%; /* Maximum width of the table */
    margin: 0 auto; /* Center the table */
  }

</style>




  