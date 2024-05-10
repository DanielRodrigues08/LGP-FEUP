<template>
    <div class="users">
      <div class="title-container">
        <h1>Admin area</h1>
        <router-link to="/add-user">
            <Button class="add-button" label="New User" icon="pi pi-plus" />
        </router-link>
      </div>
      
      <div class="search-container">
        <div>
          <InputText v-model="globalFilter.value" placeholder="Search for name" @input="filterUsers" />
        </div>
      </div>

      <DataTable class="custom-table" :value="users" :globalFilter="globalFilter" @filter="filterUsers">
        <Column field="id" header="ID"></Column>
        <Column field="name" header="Name"></Column>
        <Column field="email" header="Email"></Column>
        <Column field="phoneNumber" header="Phone"></Column>
        <Column field="permissionLevel" header="Permissions"></Column>
        <Column header="">
          <template #body="slotProps">
            <Button class="edit-button" @click="editUser(slotProps.data)">
              <i class="pi pi-pencil" style="color: grey;"></i>
            </Button>
          </template>
      </Column>
      </DataTable>

      <Dialog v-model="editDialogVisible" header="Edit User Permissions" :visible="editDialogVisible" modal :closable="false">
        <div class="p-field">
          <label for="permissionLevel">Permission Level:</label>
          <Dropdown v-model="tempPermissionLevel" id="permissionLevel" :options="permissionOptions" optionLabel="label" />
        </div>
        <div class="dialog-buttons">
          <Button label="Cancel" class="p-button-secondary" @click="cancelEdit" outlined />
          <Button label="Save" class="p-button-success" @click="saveEditedUser" outlined />
        </div>
      </Dialog>

    </div>
  </template>
  
  <script>
  import DataTable from 'primevue/datatable';
  import Column from 'primevue/column'; 
  import axios from 'axios';
  import Button from 'primevue/button';
  import InputText from 'primevue/inputtext';
  import Dialog from 'primevue/dialog';
  import Dropdown from 'primevue/dropdown';

  export default {
    components: {
      DataTable,
      Column,
      Button,
      InputText,
      Dialog,
      Dropdown
    },
    data() {
      return {
        users: [],
        originalUsers: [],
        globalFilter: { value: null, matchMode: 'contains', field: 'name' },
        editDialogVisible: false,
        selectedUser: null,
        tempPermissionLevel: null,
        permissionOptions: [
        { value: 'ADMIN', label: 'Admin' },
        { value: 'HR', label: 'HR' },
        { value: 'EMPLOYEE', label: 'Employee' }
      ]
      };
    },
    mounted() {
      this.fetchUsers();
    },
    methods: {
      async fetchUsers() {
        try {
          const response = await axios.get('http://localhost:8081/users');
          this.users = response.data;
          this.originalUsers = response.data;
        } catch (error) {
          console.error('Error fetching users:', error);
        }
      },
      editUser(user) {
        this.selectedUser = user;
        this.editDialogVisible = true;
      },
      cancelEdit() {
        this.editDialogVisible = false;
        this.selectedUser = null;
      },
      async saveEditedUser() {
        this.selectedUser.permissionLevel = this.tempPermissionLevel.value;
        try {
          // await axios.post(`http://localhost:8081/users/${this.selectedUser.id}/permissions`, {
          //   permissionLevel: this.tempPermissionLevel
          // });
          console.log('Permission updated successfully!');
        } catch (error) {
          console.error('Error updating permission:', error);
        }
        this.tempPermissionLevel = null;
        this.editDialogVisible = false;
        this.selectedUser = null;
      },
      filterUsers() {
        this.globalFilter = {
          value: this.globalFilter.value,
          matchMode: 'contains',
          field: 'name'
        };
        if (this.globalFilter.value === '') {
          this.users = this.originalUsers;
        } else {
          this.users = this.originalUsers.filter(item => item.name.toLowerCase().includes(this.globalFilter.value.toLowerCase()));
        }
      }
    }
  };
  </script>
  
  <style scoped>
    h1 {
      color: #033A65;
      font-weight:600;
      margin-right: 2rem;
    }
    .add-button {
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
      max-width: 90%;
      margin: 0 auto;
      margin-top: 1em;
      margin-bottom: 1em;

    }
    .edit-button {
      background-color: transparent;
      border: none;
      cursor: pointer;
    }
    .dialog-buttons {
      display: flex;
      justify-content: flex-end;
      margin-top: 1rem;
    }
  
    .dialog-buttons > .p-button {
      margin-left: 0.5rem;
    }
    label {
      margin-right: 1rem;
    }

    .p-dropdown {
      min-width: 10em;
    }
  </style>
  