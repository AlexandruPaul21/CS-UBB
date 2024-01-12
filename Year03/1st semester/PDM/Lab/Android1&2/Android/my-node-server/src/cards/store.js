import dataStore from 'nedb-promise';

export class CardsStore {
    constructor({filename, autoload}) {
        this.store = dataStore({filename, autoload});
    }

    async find(props) {
        return this.store.find(props);
    }

    async insert(card) {
        return this.store.insert(card);
    };

    async update(props, card) {
        return this.store.update(props, card);
    }
}

export default new CardsStore({filename: './db/cards.json', autoload: true});