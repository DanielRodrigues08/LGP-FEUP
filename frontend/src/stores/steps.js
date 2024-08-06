import { faker } from '@faker-js/faker';

faker.seed(10010101);

export function createRandomStep() {
    return {
        title: faker.word.words(3),
        description: faker.lorem.sentence({min: 5, max: 12}),
        owner: faker.person.fullName(),
        backup: faker.person.fullName(),
        deadline: faker.number.int({min: 2, max: 90}),
    }
}

export const randomSteps = faker.helpers.multiple(createRandomStep, { 
    count: 15, 
});