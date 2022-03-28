<template>
  <div>
    <div class="container">
      <el-row :gutter="10">
        <el-col :span="12">
          <div class="cz_floor_plan">
            <el-tag @click="onClick()" style="cursor: pointer">Room #9</el-tag>
            <el-row>
<!--              <el-col :span="3">-->
<!--                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>-->
<!--              </el-col>-->
<!--              <el-col :span="3">-->
<!--                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>-->
<!--              </el-col>-->
<!--              <el-col :span="3">-->
<!--                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>-->
<!--              </el-col>-->
<!--              <el-col :span="3">-->
<!--                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>-->
<!--              </el-col>-->
<!--              <el-col :span="3">-->
<!--                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>-->
<!--              </el-col>-->
<!--              <el-col :span="3">-->
<!--                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>-->
<!--              </el-col>-->
<!--              <el-col :span="3">-->
<!--                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>-->
<!--              </el-col>-->
<!--              <el-col :span="3">-->
<!--                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type" :body-style="{ paddingLeft: '0px',paddingRight: '0px',paddingTop:'11px',paddingBottom:'11px',backgroundColor:'green',cursor: 'pointer'}">-->
<!--                  <el-tooltip placement="top">-->
<!--                    <template #content>-->
<!--                      <p>Mold: AP4-000</p>-->
<!--                      <p>Side: S1</p>-->
<!--                      <p>Process: Fine</p>-->
<!--                      <p>ML: 02</p>-->
<!--                      <p>OP id: XX</p>-->
<!--                    </template>-->
<!--                    <p style="text-align: center;font-weight: bold;">FG 902</p>-->
<!--                  </el-tooltip>-->
<!--                </el-card>-->
<!--              </el-col>-->
              <el-col v-for="(machineInfo, index) of machineInfoByRoom(9)" :span="3" :key="index">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type" :body-style="{ paddingLeft: '0px',paddingRight: '0px',paddingTop:'11px',paddingBottom:'11px',backgroundColor:'green',cursor: 'pointer'}">
                  <el-tooltip placement="top">
                    <template #content>
                      <p>Mold: {{machineInfo.mold}}</p>
                      <p>Side: {{machineInfo.side}}</p>
                      <p>Process: {{machineInfo.process}}</p>
                      <p>ML: {{machineInfo.ml}}</p>
                      <p>OP id: {{machineInfo.opid}}</p>
                    </template>
                    <p style="text-align: center;font-weight: bold;">{{machineInfo.name}}</p>
                  </el-tooltip>
                </el-card>
              </el-col>
            </el-row>


<!--            <el-row>-->
<!--              <el-col :span="3">-->
<!--                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>-->
<!--              </el-col>-->
<!--              <el-col :span="3">-->
<!--                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>-->
<!--              </el-col>-->
<!--              <el-col :span="3">-->
<!--                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>-->
<!--              </el-col>-->
<!--              <el-col :span="3">-->
<!--                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>-->
<!--              </el-col>-->
<!--              <el-col :span="3">-->
<!--                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>-->
<!--              </el-col>-->
<!--              <el-col :span="3">-->
<!--                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>-->
<!--              </el-col>-->
<!--              <el-col :span="3">-->
<!--                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>-->
<!--              </el-col>-->
<!--              <el-col :span="3">-->
<!--                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>-->
<!--              </el-col>-->
<!--            </el-row>-->
          </div>
        </el-col>

        <el-col :span="12">
          <div class="cz_floor_plan">
            <el-tag>Room #4</el-tag>
            <el-row>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
            </el-row>
          </div>
        </el-col>
      </el-row>

      <el-row :gutter="10">
        <el-col :span="12">
          <div class="cz_floor_plan">
            <el-tag>Room #8</el-tag>
            <el-row>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
            </el-row>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="cz_floor_plan">
            <el-tag>Room #3</el-tag>
            <el-row>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
            </el-row>
          </div>
        </el-col>
      </el-row>

      <el-row :gutter="10">
        <el-col :span="12">
          <div class="cz_floor_plan">
            <el-tag>Room #7</el-tag>
            <el-row>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
            </el-row>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="cz_floor_plan">
            <el-row>
              <el-col :span="4"><div class="grid-content bg-blue"><span style="color: white; font-size:13px">Cleaning room</span></div></el-col>
              <el-col :span="4"><div class="grid-content bg-blue"><span style="color: white; ">Mikura</span></div></el-col>
              <el-col :span="4"><div class="grid-content bg-blue"><span style="color: white; ">Microscope</span></div></el-col>
              <el-col :span="4"><div class="grid-content bg-blue"><span style="color: white; ">Microscope</span></div></el-col>
              <el-col :span="4"><div class="grid-content bg-blue"><span style="color: white; ">Microscope</span></div></el-col>
              <el-col :span="2"><div class="grid-content bg-blue"><span style="color: white; ">UA3P</span></div></el-col>
              <el-col :span="2"><div class="grid-content bg-blue"><span style="color: white; ">UA3P</span></div></el-col>
            </el-row>
          </div>
        </el-col>
      </el-row>

      <el-row :gutter="10">
        <el-col :span="12">
          <div class="cz_floor_plan">
            <el-tag>Room #6</el-tag>
            <el-row>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
            </el-row>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="cz_floor_plan">
            <el-tag>Room #2</el-tag>
            <el-row>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
            </el-row>
          </div>
        </el-col>
      </el-row>

      <el-row :gutter="10">
        <el-col :span="12">
          <div class="cz_floor_plan">
            <el-tag>Room #5</el-tag>
            <el-row>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
            </el-row>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="cz_floor_plan">
            <el-tag>Room #1</el-tag>
            <el-row>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
              <el-col :span="3">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type"></el-card>
              </el-col>
            </el-row>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  name: "FloorPlan",
  methods: {
    onClick() {
      console.log("test");
      //this.$router.replace('/roomDetail');
      this.$router.push({name: 'roomDetail'});
    },
    machineInfoByRoom(roomNum) {
      const res = []
      if(roomNum == 9) {
        res.push({
          'name': 'FG901',
          'mold': 'AP4-000',
          'side': 'S1',
          'process': 'Fine',
          'ml': '02',
          'opid': 'xx'
        },
            {
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx'
            },
            {
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx'
            },
            {
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx'
            },
            {
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx'
            },
            {
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx'
            },
            {
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx'
            },
            {
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx'
            },
            {
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx'
            },
            {
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx'
            },
            {
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx'
            },
            {
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx'
            },
            {
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx'
            },
            {
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx'
            },
            {
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx'
            },
            {
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx'
            });
      }
      return res
    }
  }
}
</script>

<style scoped>

</style>