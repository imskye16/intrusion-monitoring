<template>
  <!-- 顶部标题栏 -->
  <div class="header-bar">
    上海海平面上升风险预警决策系统
  </div>

  <!-- 选项栏（两个下拉框） -->
  <div class="option-bar">
    <!-- 下拉框1：可自定义文字 -->
    <div class="dropdown">
      <button class="dropdown-btn">海平面上升精细比预测 ▾</button>
      <div class="dropdown-menu">
        <div class="menu-item">重大洪涝海平面上升风险预警</div>
        <div class="menu-item">海平面上升下降线风险预警</div>
        <div class="menu-item">海岸生态系统风险预警</div>
      </div>
    </div>

    <!-- 下拉框2：可自定义文字 -->
    <div class="dropdown">
      <button class="dropdown-btn">海平面上升影响 ▾</button>
      <div class="dropdown-menu">
        <div class="menu-item">海平面上升下降城市事件风险预警</div>
        <div class="menu-item">江苏沿海洪水风险预警</div>
        <div class="menu-item">江苏风暴潮风险预警</div>
      </div>
    </div>
  </div>

  <!-- 左侧功能按钮组 -->
  <div class="function-btns">
    <button class="func-btn">地域系统海平面上升预测展示</button>
    <button class="func-btn">区域精细化海平面上升预测展示</button>
    <button class="func-btn">极端海平面上升预测展示</button>
  </div>

  <!-- 地图容器 -->
  <div id="map"></div>
</template>

<script setup>
import { onMounted } from 'vue'

// 页面挂载后初始化地图
onMounted(() => {
  // 1. 初始化地图（上海区域）
  const map = L.map('map').setView([31.2304, 121.4737], 10);

  // 2. 加载高德底图
  L.tileLayer('https://webrd0{s}.is.autonavi.com/appmaptile?lang=zh_cn&size=1&scale=1&style=8&x={x}&y={y}&z={z}', {
    subdomains: ['1', '2', '3', '4'],
    attribution: '© 高德地图'
  }).addTo(map);

  // 3. 加载后端GeoJSON并渲染线条（保留原有逻辑）
  fetch("/api/shp/geojson")
    .then(response => {
      if (!response.ok) throw new Error("接口请求失败");
      return response.json();
    })
    .then(geoJsonData => {
      L.geoJSON(geoJsonData, {
        style: () => ({
          color: "#ff0000",
          weight: 6,
          opacity: 0.8,
          lineCap: "round"
        }),
        onEachFeature: (feature, layer) => {
          layer.on('mouseover', function () {
            this.setStyle({ weight: 8, color: "#ff6600" });
          });
          layer.on('mouseout', function () {
            this.setStyle({ weight: 6, color: "#ff0000" });
          });
        }
      }).addTo(map);
      const bounds = L.geoJSON(geoJsonData).getBounds();
      map.fitBounds(bounds);
    })
    .catch(error => {
      console.error("加载失败：", error);
      alert("地图数据加载失败，请检查后端服务");
    });

  // 4. 下拉框交互逻辑
  const dropdownBtns = document.querySelectorAll('.dropdown-btn');
  dropdownBtns.forEach(btn => {
    btn.addEventListener('click', () => {
      const menu = btn.nextElementSibling;
      menu.classList.toggle('show');
    });
  });
  // 点击其他区域关闭下拉框
  document.addEventListener('click', (e) => {
    if (!e.target.closest('.dropdown')) {
      document.querySelectorAll('.dropdown-menu').forEach(menu => {
        menu.classList.remove('show');
      });
    }
  });
});
</script>

<style scoped>
/* 全局重置 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
html, body, #app {
  width: 100%;
  height: 100%;
  overflow: hidden;
}

/* 标题栏样式（科技感蓝渐变） */
.header-bar {
  width: 100%;
  height: 60px;
  background: linear-gradient(to right, #002244, #0066cc, #002244);
  color: #ffffff;
  font-size: 24px;
  font-weight: bold;
  text-align: center;
  line-height: 60px;
  letter-spacing: 2px;
  text-shadow: 0 0 10px #00ccff;
  border-bottom: 2px solid #00ccff;
  position: relative;
  z-index: 999;
}

/* 选项栏（标题栏下方的两个下拉框） */
.option-bar {
  height: 40px;
  background: linear-gradient(to right, #003366, #0055aa);
  display: flex;
  align-items: center;
  padding: 0 20px;
  gap: 20px;
  border-bottom: 1px solid #00ccff;
  z-index: 998;
  position: relative;
}
.dropdown {
  position: relative;
}
.dropdown-btn {
  background: #004488;
  color: #ffffff;
  border: 1px solid #00ccff;
  padding: 5px 15px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  display: flex;
  align-items: center;
  gap: 5px;
}
.dropdown-btn:hover {
  background: #0055aa;
}
.dropdown-menu {
  position: absolute;
  top: 100%;
  left: 0;
  width: 220px;
  background: #002244;
  border: 1px solid #00ccff;
  border-radius: 4px;
  padding: 10px 0;
  display: none;
  z-index: 999;
}
.dropdown-menu.show {
  display: block;
}
.menu-item {
  color: #ffffff;
  padding: 8px 15px;
  font-size: 13px;
  cursor: pointer;
}
.menu-item:hover {
  background: #0055aa;
}

/* 左侧功能按钮组 */
.function-btns {
  position: absolute;
  top: 110px;
  left: 20px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  z-index: 997;
}
.func-btn {
  background: rgba(0, 51, 102, 0.8);
  color: #ffffff;
  border: 1px solid #00ccff;
  padding: 10px 15px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  text-align: left;
}
.func-btn:hover {
  background: rgba(0, 85, 170, 0.9);
}

/* 地图容器 */
#map {
  width: 100%;
  height: calc(100% - 100px); /* 减去标题栏(60px)+选项栏(40px)高度 */
  position: relative;
  z-index: 996;
}
</style>