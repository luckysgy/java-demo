import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/websocket',
    name: 'Websocket',
    component: () => import(/* webpackChunkName: "about" */ '../views/Websocket.vue')
  },
  {
    path: '/flvLiveStream',
    name: 'FlvLiveStream',
    component: () => import(/* webpackChunkName: "about" */ '../views/FlvLiveStream.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
