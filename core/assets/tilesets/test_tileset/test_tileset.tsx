<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.2" tiledversion="1.3.3" name="test_tileset" tilewidth="256" tileheight="256" tilecount="3" columns="0">
 <grid orientation="orthogonal" width="1" height="1"/>
 <tile id="0">
  <image width="256" height="256" source="grass.png"/>
 </tile>
 <tile id="1">
  <image width="256" height="256" source="90_left_road.png"/>
  <objectgroup draworder="index" id="2">
   <object id="3" x="0" y="0">
    <polyline points="131,0 123,82 0,134"/>
   </object>
   <object id="4" type="roadPolygon" x="179.75" y="-1.25">
    <polygon points="0,0 -1,30 -2.5,49.75 -2,68.5 0.5,86.5 1.25,100.5 -7,119.5 -24.5,140 -55.75,164.25 -65.5,170 -74.25,170.5 -99.75,180.5 -125,186.5 -146.25,188.5 -179.5,184.5 -180.5,184.25 -180.25,72.25 -175.25,70.5 -172,72 -161.25,61.75 -151.75,56 -141.75,42.5 -127.75,33.75 -113.25,17 -108.25,7 -109.25,0"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="2">
  <image width="256" height="256" source="straight_road.png"/>
  <objectgroup draworder="index" id="2">
   <object id="1" x="123" y="-1">
    <polyline points="0,0 4,256"/>
   </object>
   <object id="2" type="roadPolygon" x="71.4545" y="256">
    <polygon points="0,0 107.455,0 107.273,-22 109.636,-27.8182 110.182,-34.9091 110.909,-55.2727 104.727,-76 104.545,-105.273 104.909,-134.727 102.182,-147.455 100.909,-176.182 106.909,-205.636 106.909,-230.182 105.455,-255.818 -0.363636,-256.182 -1.63636,-228 -2.18182,-204.364 -1.45455,-195.455 2.18182,-182.182 4.72727,-177.091 3.81818,-169.091 -2.36364,-151.818 -6,-132.545 -6,-111.818 -2.72727,-102.909 0.545455,-84.3636 1.81818,-60 4,-48.5455 1.63636,-37.4545 -1.81818,-24.1818 -0.545455,-16.7273 0,-11.4545"/>
   </object>
  </objectgroup>
 </tile>
</tileset>
