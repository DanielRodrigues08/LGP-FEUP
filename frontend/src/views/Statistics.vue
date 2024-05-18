<template>
  <div class="title-container">
    <h1>Onboardees Statistics</h1>
    </div>
  <div class="chart-container">
    <Chart :type="'pie'" :data="nationalityChartData" :options="nationalityChartOptions" />
    <div class="surface-card shadow-2 p-3 border-round">
      <div class="flex justify-content-between mb-3">
        <div>
          <span class="block text-500 font-medium mb-3">Number of Onboardees</span>
          <div class="text-900 font-medium text-xl">{{ filteredOnboardees.length }}</div>
        </div>
        <div class="flex align-items-center justify-content-center bg-blue-100 border-round" style="width:2.5rem;height:2.5rem">
          <i class="pi pi-users text-blue-500 text-xl"></i>
        </div>
      </div>
    </div>
    <Chart :type="'doughnut'" :data="genderChartData" :options="genderChartOptions" />
  </div>
  <div class="filter-container">
    <div class="p-inputgroup">
      <span class="p-inputgroup-addon">
        <i class="pi pi-calendar"></i>
      </span>
      <Calendar v-model="startDate" placeholder="Start Date" :minDate="minDate" :maxDate="maxDate" />
      <Calendar v-model="endDate" placeholder="End Date" :minDate="minDate" :maxDate="maxDate" />
      <Button label="Apply" @click="filterData" />
    </div>
  </div>
</template>



<script>
import axios from 'axios';
import Chart from 'primevue/chart';
import Calendar from 'primevue/calendar';
import Button from 'primevue/button';

export default {
  components: {
    Chart,
    Calendar,
    Button
  },
  data() {
    return {
      onboardees: [],
      filteredOnboardees: [],
      nationalityChartData: null,
      genderChartData: null,
      nationalityChartOptions: {
        title: {
          display: true,
          text: 'Nationalities of Onboardees',
          fontColor: '#FFFFFF' // White text for better contrast
        },
        legend: {
          position: 'bottom',
          labels: {
            fontColor: '#FFFFFF' // White text for better contrast
          }
        }
      },
      genderChartOptions: {
        title: {
          display: true,
          text: 'Gender Distribution',
          fontColor: '#FFFFFF' // White text for better contrast
        },
        legend: {
          position: 'bottom',
          labels: {
            fontColor: '#FFFFFF' // White text for better contrast
          }
        }
      },
      startDate: null,
      endDate: null,
      minDate: null,
      maxDate: null
    };
  },
  mounted() {
    this.fetchOnboardees();
  },
  methods: {
    async fetchOnboardees() {
      try {
        const response = await axios.get(`${import.meta.env.VITE_API_URL}/onboardees`);
        this.onboardees = response.data;
        this.calculateMinMaxDate();
        this.filteredOnboardees = response.data;
        this.generateNationalityChartData();
        this.generateGenderChartData();
      } catch (error) {
        console.error('Error fetching onboardees:', error);
      }
    },
    calculateMinMaxDate() {
      if (this.onboardees.length === 0) return;

      const startDates = this.onboardees.map(onboardee => new Date(onboardee.startDate));
      const earliestDate = new Date(Math.min(...startDates));
      const latestDate = new Date(Math.max(...startDates));

      // Set minDate to the earliest date and maxDate to the latest date
      this.minDate = earliestDate;
      this.maxDate = latestDate;
    },
    generateNationalityChartData() {
      const nationalityCounts = {};
      this.filteredOnboardees.forEach(onboardee => {
        if (nationalityCounts[onboardee.nationality]) {
          nationalityCounts[onboardee.nationality]++;
        } else {
          nationalityCounts[onboardee.nationality] = 1;
        }
      });

      this.nationalityChartData = {
        labels: Object.keys(nationalityCounts),
        datasets: [
          {
            data: Object.values(nationalityCounts),
            backgroundColor: [
                '#3B82F6', // Blue
                '#818CF8', // Light Indigo
                '#FBBF24', // Light Yellow
                '#60A5FA', // Light Blue
                '#34D399', // Green
                '#6366F1', // Indigo
                '#A78BFA', // Light Purple
                '#1E3A8A', // Dark Blue
                '#F59E0B', // Yellow
                '#8B5CF6', // Purple
                '#10B981', // Emerald
                '#22D3EE', // Cyan
                '#6EE7B7', // Light Green
                '#A5B4FC',  // Light Blueish Purple
                '#FCD34D'  // Pale Yellow
            ],
            hoverBackgroundColor: [
            '#85baf6', // Lighter Blue
              '#b4bdf8', // Lighter Indigo
              '#fcefa4', // Lighter Yellow
              '#add9fa', // Lighter Blue
              '#9ae2c5', // Lighter Green
              '#b8bdf9', // Lighter Purple
              '#7f8fae', // Lighter Blue
              '#6ee3a0', // Lighter Green
              '#d8dffc', // Lighter Blueish Purple
              '#edc394', // Lighter Yellow
              '#b9aef9', // Lighter Purple
              '#aff9d3', // Lighter Green
              '#a6def4', // Lighter Cyan
              '#d9eef1', // Lighter Blue
              '#ffddc7'  // Lighter Pale Yellow
            ]
          }
        ]
      };
    },
    generateGenderChartData() {
      const genderCounts = {
        Male: 0,
        Female: 0,
        Other: 0
      };

      this.filteredOnboardees.forEach(onboardee => {
        if (onboardee.gender === 'male') {
          genderCounts.Male++;
        } else if (onboardee.gender === 'female') {
          genderCounts.Female++;
        } else {
          genderCounts.Other++;
        }
      });

      this.genderChartData = {
        labels: ['Male', 'Female', 'Other'],
        datasets: [
          {
            data: Object.values(genderCounts),
            backgroundColor: ['#60A5FA', '#FCD34D', '#6366F1'],
            hoverBackgroundColor: ['#add9fa', '#fcefa4', '#b8bdf9']
          }
        ]
      };
    },
    filterData() {
      this.startDate.setHours(0, 0, 0, 0);
      this.endDate.setHours(23, 59, 59, 999);

      // Filter onboardees based on startDate and endDate
      this.filteredOnboardees = this.onboardees.filter(onboardee => {
        const startDate = new Date(onboardee.startDate);
        startDate.setHours(0, 0, 0, 0);

        return startDate >= this.startDate && startDate <= this.endDate;
      });
      // Update charts based on filtered data
      this.generateNationalityChartData();
      this.generateGenderChartData();
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
.title-container {
  display: flex;
  align-items: center;
  margin-top: 1.5rem;
  margin-left: 2rem;
}

.chart-container {
  display: flex;
  justify-content: space-around;
  align-items: center;
  width: 100%;
  padding: 20px;
  border-radius: 10px;
}

.surface-card {
  width: 300px;
}

.date-range-container {
  margin-top: 20px;
}
.filter-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 80%;
  margin: 3em auto;
}

.date-range-container input,
.date-range-container button {
  margin-right: 10px;
}
</style>
