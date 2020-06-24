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
  </objectgroup>
 </tile>
 <tile id="2">
  <image width="256" height="256" source="straight_road.png"/>
  <objectgroup draworder="index" id="2">
   <object id="1" x="123" y="-1">
    <polyline points="0,0 4,256"/>
   </object>
  </objectgroup>
 </tile>
</tileset>
