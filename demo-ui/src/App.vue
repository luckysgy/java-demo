<template>
  <div id="nav">
    <a-dropdown :placement="bottomCenter">
        <a-button type="primary">功能列表</a-button>
        <template #overlay>
          <a-menu>
            <a-menu-item>
              <router-link to="/">主页</router-link>
            </a-menu-item>
            
            <a-menu-item>
              <router-link to="/websocket">Websocket演示</router-link>
            </a-menu-item>

            <a-menu-item>
              <router-link to="/flvLiveStream">flv直播流</router-link>
            </a-menu-item>
            
          </a-menu>
        </template>
      </a-dropdown>
  </div>
  <a-divider>展示功能</a-divider>
  <router-view/>
</template>

<style>
#app {
  margin: 20px;
}

</style>
