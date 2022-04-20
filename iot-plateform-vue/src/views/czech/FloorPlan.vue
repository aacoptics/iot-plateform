<template>
  <div v-if="this.showContent">
    <div class="container">
      <el-row :gutter="10">
        <el-col :span="12">
          <div class="cz_floor_plan">
            <el-tag @click="roomDetail(9)" style="cursor: pointer">Room #9</el-tag>
            <el-row>
              <el-col v-for="(machineInfo, index) of this.room9Info" :span="3" :key="index">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type" :body-style="{ paddingLeft: '0px',paddingRight: '0px',paddingTop:'11px',paddingBottom:'11px',backgroundColor:'green',cursor: 'pointer'}">
                  <el-tooltip placement="top">
                    <template #content>
                      <p>Mold: {{machineInfo.mold}}</p>
                      <p>Side: {{machineInfo.side}}</p>
                      <p>Process: {{machineInfo.process}}</p>
                      <p>ML: {{machineInfo.ml}}</p>
                      <p>OP id: {{}}</p>
                    </template>
                    <p style="text-align: center;font-weight: bold;">FG{{machineInfo.machineNo}}</p>
                  </el-tooltip>
                </el-card>
              </el-col>
            </el-row>

          </div>
        </el-col>

        <el-col :span="12">
          <div class="cz_floor_plan">
            <el-tag @click="roomDetail(4)" style="cursor: pointer">Room #4</el-tag>
            <el-row>
              <el-col v-for="(machineInfo, index) of this.room4Info" :span="3" :key="index">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type" :body-style="{ paddingLeft: '0px',paddingRight: '0px',paddingTop:'11px',paddingBottom:'11px',backgroundColor:'green',cursor: 'pointer'}">
                  <el-tooltip placement="top">
                    <template #content>
                      <p>Mold: {{machineInfo.mold}}</p>
                      <p>Side: {{machineInfo.side}}</p>
                      <p>Process: {{machineInfo.process}}</p>
                      <p>ML: {{machineInfo.ml}}</p>
                      <p>OP id: {{}}</p>
                    </template>
                    <p style="text-align: center;font-weight: bold;">FG{{machineInfo.machineNo}}</p>
                  </el-tooltip>
                </el-card>
              </el-col>
            </el-row>
          </div>
        </el-col>
      </el-row>

      <el-row :gutter="10">
        <el-col :span="12">
          <div class="cz_floor_plan">
            <el-tag @click="roomDetail(8)" style="cursor: pointer">Room #8</el-tag>
            <el-row>
              <el-col v-for="(machineInfo, index) of this.room8Info" :span="3" :key="index">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type" :body-style="{ paddingLeft: '0px',paddingRight: '0px',paddingTop:'11px',paddingBottom:'11px',backgroundColor:'green',cursor: 'pointer'}">
                  <el-tooltip placement="top">
                    <template #content>
                      <p>Mold: {{machineInfo.mold}}</p>
                      <p>Side: {{machineInfo.side}}</p>
                      <p>Process: {{machineInfo.process}}</p>
                      <p>ML: {{machineInfo.ml}}</p>
                      <p>OP id: {{}}</p>
                    </template>
                    <p style="text-align: center;font-weight: bold;">FG{{machineInfo.machineNo}}</p>
                  </el-tooltip>
                </el-card>
              </el-col>
            </el-row>
          </div>
        </el-col>

        <el-col :span="12">
          <div class="cz_floor_plan">
            <el-tag @click="roomDetail(3)" style="cursor: pointer">Room #3</el-tag>
            <el-row>
              <el-col v-for="(machineInfo, index) of this.room3Info" :span="3" :key="index">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type" :body-style="{ paddingLeft: '0px',paddingRight: '0px',paddingTop:'11px',paddingBottom:'11px',backgroundColor:'green',cursor: 'pointer'}">
                  <el-tooltip placement="top">
                    <template #content>
                      <p>Mold: {{machineInfo.mold}}</p>
                      <p>Side: {{machineInfo.side}}</p>
                      <p>Process: {{machineInfo.process}}</p>
                      <p>ML: {{machineInfo.ml}}</p>
                      <p>OP id: {{}}</p>
                    </template>
                    <p style="text-align: center;font-weight: bold;">FG{{machineInfo.machineNo}}</p>
                  </el-tooltip>
                </el-card>
              </el-col>
            </el-row>
          </div>
        </el-col>
      </el-row>

      <el-row :gutter="10">
        <el-col :span="12">
          <div class="cz_floor_plan">
            <el-tag @click="roomDetail(7)" style="cursor: pointer">Room #7</el-tag>
            <el-row>
              <el-col v-for="(machineInfo, index) of this.room7Info" :span="3" :key="index">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type" :body-style="{ paddingLeft: '0px',paddingRight: '0px',paddingTop:'11px',paddingBottom:'11px',backgroundColor:'green',cursor: 'pointer'}">
                  <el-tooltip placement="top">
                    <template #content>
                      <p>Mold: {{machineInfo.mold}}</p>
                      <p>Side: {{machineInfo.side}}</p>
                      <p>Process: {{machineInfo.process}}</p>
                      <p>ML: {{machineInfo.ml}}</p>
                      <p>OP id: {{}}</p>
                    </template>
                    <p style="text-align: center;font-weight: bold;">FG{{machineInfo.machineNo}}</p>
                  </el-tooltip>
                </el-card>
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
            <el-tag @click="roomDetail(6)" style="cursor: pointer">Room #6</el-tag>
            <el-row>
              <el-col v-for="(machineInfo, index) of this.room6Info" :span="3" :key="index">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type" :body-style="{ paddingLeft: '0px',paddingRight: '0px',paddingTop:'11px',paddingBottom:'11px',backgroundColor:'green',cursor: 'pointer'}">
                  <el-tooltip placement="top">
                    <template #content>
                      <p>Mold: {{machineInfo.mold}}</p>
                      <p>Side: {{machineInfo.side}}</p>
                      <p>Process: {{machineInfo.process}}</p>
                      <p>ML: {{machineInfo.ml}}</p>
                      <p>OP id: {{}}</p>
                    </template>
                    <p style="text-align: center;font-weight: bold;">FG{{machineInfo.machineNo}}</p>
                  </el-tooltip>
                </el-card>
              </el-col>
            </el-row>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="cz_floor_plan">
            <el-tag @click="roomDetail(2)" style="cursor: pointer">Room #2</el-tag>
            <el-row>
              <el-col v-for="(machineInfo, index) of this.room2Info" :span="3" :key="index">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type" :body-style="{ paddingLeft: '0px',paddingRight: '0px',paddingTop:'11px',paddingBottom:'11px',backgroundColor:'green',cursor: 'pointer'}">
                  <el-tooltip placement="top">
                    <template #content>
                      <p>Mold: {{machineInfo.mold}}</p>
                      <p>Side: {{machineInfo.side}}</p>
                      <p>Process: {{machineInfo.process}}</p>
                      <p>ML: {{machineInfo.ml}}</p>
                      <p>OP id: {{}}</p>
                    </template>
                    <p style="text-align: center;font-weight: bold;">FG{{machineInfo.machineNo}}</p>
                  </el-tooltip>
                </el-card>
              </el-col>
            </el-row>
          </div>
        </el-col>
      </el-row>

      <el-row :gutter="10">
        <el-col :span="12">
          <div class="cz_floor_plan">
            <el-tag @click="roomDetail(5)" style="cursor: pointer">Room #5</el-tag>
            <el-row>
              <el-col v-for="(machineInfo, index) of this.room5Info" :span="3" :key="index">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type" :body-style="{ paddingLeft: '0px',paddingRight: '0px',paddingTop:'11px',paddingBottom:'11px',backgroundColor:'green',cursor: 'pointer'}">
                  <el-tooltip placement="top">
                    <template #content>
                      <p>Mold: {{machineInfo.mold}}</p>
                      <p>Side: {{machineInfo.side}}</p>
                      <p>Process: {{machineInfo.process}}</p>
                      <p>ML: {{machineInfo.ml}}</p>
                      <p>OP id: {{}}</p>
                    </template>
                    <p style="text-align: center;font-weight: bold;">FG{{machineInfo.machineNo}}</p>
                  </el-tooltip>
                </el-card>
              </el-col>
            </el-row>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="cz_floor_plan">
            <el-tag @click="roomDetail(1)" style="cursor: pointer">Room #1</el-tag>
            <el-row>
              <el-col v-for="(machineInfo, index) of this.room1Info" :span="3" :key="index">
                <el-card style="border:1px solid blue;margin:5px;" class="cz_floor_type" :body-style="{ paddingLeft: '0px',paddingRight: '0px',paddingTop:'11px',paddingBottom:'11px',backgroundColor:'green',cursor: 'pointer'}">
                  <el-tooltip placement="top">
                    <template #content>
                      <p>Mold: {{machineInfo.mold}}</p>
                      <p>Side: {{machineInfo.side}}</p>
                      <p>Process: {{machineInfo.process}}</p>
                      <p>ML: {{machineInfo.ml}}</p>
                      <p>OP id: {{}}</p>
                    </template>
                    <p style="text-align: center;font-weight: bold;">FG{{machineInfo.machineNo}}</p>
                  </el-tooltip>
                </el-card>
              </el-col>
            </el-row>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import {
  getFloorPlanMachineInfo
} from "@/api/czech/floorPlan";
export default {
  name: "FloorPlan",
  data() {
    return {
      room1Info: [],
      room2Info: [],
      room3Info: [],
      room4Info: [],
      room5Info: [],
      room6Info: [],
      room7Info: [],
      room8Info: [],
      room9Info: [],
      showContent: false
    }
  },
  created() {
    this.getMachineInfo();
  },
  methods: {
    roomDetail(floorNumber) {
      this.$router.push({name: 'roomDetail', params:{floorNumber: floorNumber}});
      // console.log(floorNumber);
      // this.$router.push({name: 'roomDetail'});
    },
    getMachineInfo() {
      getFloorPlanMachineInfo(901, 916).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          responseData.data.forEach(item => {
            this.room9Info.push(item)
            this.showContent = true
          })
        }
      });

      getFloorPlanMachineInfo(801, 816).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          responseData.data.forEach(item => {
            this.room8Info.push(item)
            this.showContent = true
          })
        }
      });

      getFloorPlanMachineInfo(701, 716).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          responseData.data.forEach(item => {
            this.room7Info.push(item)
            this.showContent = true
          })
        }
      });

      getFloorPlanMachineInfo(601, 616).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          responseData.data.forEach(item => {
            this.room6Info.push(item)
            this.showContent = true
          })
        }
      });

      getFloorPlanMachineInfo(501, 516).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          responseData.data.forEach(item => {
            this.room5Info.push(item)
            this.showContent = true
          })
        }
      });

      getFloorPlanMachineInfo(401, 416).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          responseData.data.forEach(item => {
            this.room4Info.push(item)
            this.showContent = true
          })
        }
      });

      getFloorPlanMachineInfo(301, 316).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          responseData.data.forEach(item => {
            this.room3Info.push(item)
            this.showContent = true
          })
        }
      });

      getFloorPlanMachineInfo(201, 216).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          responseData.data.forEach(item => {
            this.room2Info.push(item)
            this.showContent = true
          })
        }
      });

      getFloorPlanMachineInfo(101, 116).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          responseData.data.forEach(item => {
            this.room1Info.push(item)
            this.showContent = true
          })
        }
      });

    },

  }
}
</script>

<style scoped>

</style>