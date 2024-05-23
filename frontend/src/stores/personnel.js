import { faker } from '@faker-js/faker';

faker.seed(10010101);

export function createRandomPersonnel() {
    return {
        name: faker.person.fullName(),
        avatar: faker.image.avatar(),
    }
}

export const randomPersonnel = faker.helpers.multiple(createRandomPersonnel, { 
    count: 10, 
});