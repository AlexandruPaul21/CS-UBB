import Router from 'koa-router';
import cardsStore from './store';
import {broadcast} from "../utils";

export const router = new Router();


router.get('/', async (ctx) => {
    const userId = ctx.state.user._id;

    let cards = await cardsStore.find({userId});

    ctx.response.body = cards;

    ctx.response.status = 200;
});

router.post('/', async ctx => {
    const userId = ctx.state.user._id;

    const card = ctx.request.body;
    delete card._id
    card.userId = userId;

    ctx.response.body = await cardsStore.insert(card);
    ctx.response.status = 201;
    broadcast(userId, {type: 'created', payload: ctx.response.body});
});

router.put('/:id', async (ctx) => {
    const userId = ctx.state.user._id;

    const card = ctx.request.body;
    card._id = ctx.params.id;
    card.userId = userId;

    await cardsStore.update({_id: ctx.params.id}, card);

    ctx.response.status = 200;
    ctx.response.body = card;

    broadcast(userId, {type: 'updated', payload: ctx.response.body});
});
