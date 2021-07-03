<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-calendar"></i> 注塑机监控
        </el-breadcrumb-item>
        <el-breadcrumb-item>新能源{{ this.$route.query.position }}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <el-row v-for="(page, index) of monitPages" :key="index" style="margin-bottom: 20px">
        <el-col :span="4" v-for="(item, innerIndex) of page" :key="item.monitMcId" :offset="innerIndex > 0 ? 2 : 1">
          <el-card :body-style="{ padding: '0px', height:'80px'}"
                   shadow="hover"
                   style="width: 230px;height: 85px;border:1px solid cornflowerblue;background-color: #f0f0f0;"
                   @click="onCardClick(index, innerIndex)">
            <el-row>
              <el-col :span="17">
                <div>
                  <p style="font-weight: bold;text-align: center">{{ item.monitMcName }}</p>
                  <p style="margin-top: 5px">{{ item.condMoldFileName }}</p>
                  <p v-if="checkMachineStatus(item.monitStatus)">
                    <span style="margin-right: 10px">{{ item.monitShotCount }}</span>
                    <span>{{ item.monitCycle }}秒</span>
                  </p>
                  <p v-if="checkMachineStatus(item.monitStatus)">
                    <span style="margin-right: 10px">{{ item.monitGoodCount }}</span>
                    <span>{{
                        this.$moment(item.monitDateTime, 'YYMMDDHHmmss')
                            .format('MM/DD HH:mm')
                      }}</span>
                  </p>
                </div>
              </el-col>
              <el-col :span="7">
                <div :class="getStatusType(item.monitStatus)">{{ getStatusName(item.monitStatus) }}
                </div>
              </el-col>
            </el-row>
          </el-card>
        </el-col>
      </el-row>

      <el-dialog
          title="注塑机详细"
          v-model="dialogVisible"
          width="98%"
          @opened="showPieChart"
          @closed="onDialogClosed">
        <el-row style="border: 1px solid #2d8cf0">
          <el-col :span="12" style="border-right: 1px solid #2d8cf0">
            <el-row>
              <div style="font-weight: bold;color: white;background-color: #2d8cf0;width: 100%">设备详细信息</div>
            </el-row>
            <el-row style="margin-bottom: 10px">
              <el-col :span="8">
                <img src="../../assets/img/FanucMachine.jpg" style="width: 100%;height: 100%"/></el-col>
              <el-col :span="10" style="font-weight: bold">
                <el-row style="margin-bottom: 10px">
                  <p>机器名：</p>
                  <p style="color: green">{{ this.dialogMachineName }}</p>
                </el-row>
                <el-row style="margin-bottom: 10px">
                  <p>当前状态：</p>
                  <p style="color: green">{{ this.status[this.fanucDialogData.monitData.Status] }}</p>
                </el-row>
                <el-row style="margin-bottom: 10px">
                  <p>模具文件名：</p>
                  <p style="color: green">{{ this.fanucDialogData.moldFileName }}</p>
                </el-row>
                <el-row style="margin-bottom: 10px">
                  <p>HR模式：</p>
                  <p style="color: green">{{ this.fanucDialogData.condData.cond_hr_mode }}</p>
                </el-row>
                <el-row style="margin-bottom: 10px">
                  <p>实时成型间隔时间(s)：</p>
                  <p style="color: green">{{ this.fanucDialogData.monitData.Cycle }}</p>
                </el-row>
                <el-row style="margin-bottom: 10px">
                  <p>最后一次成型时间：</p>
                  <p style="color: green">{{
                      this.$moment(this.fanucDialogData.monitData.Date
                          + this.fanucDialogData.monitData.Time, 'YYMMDDHHmmss')
                          .format('MM/DD HH:mm:ss')
                    }}</p>
                </el-row>
                <el-row style="margin-bottom: 10px">
                  <p>最大射出速度(mm/s)：</p>
                  <p style="color: green">{{ this.fanucDialogData.condData.cond_inj_speed1 }}</p>
                </el-row>
                <el-row style="margin-bottom: 10px">
                  <p>最大射出压力(kgf/cm2)：</p>
                  <p style="color: green">{{ this.fanucDialogData.condData.cond_max_inj_pres }}</p>
                </el-row>
                <el-row style="margin-bottom: 10px">
                  <p>保压切换模式：</p>
                  <p style="color: green">{{ this.fanucDialogData.condData.cond_trans_mode }}</p>
                </el-row>
              </el-col>
              <el-col :span="6" style="justify-content: flex-end;">
                <el-row style="font-weight: bold;font-size: large">
                  <p>成型次数</p>
                </el-row>
                <el-row style="font-family: 'led regular';font-size: xxx-large;color: green">
                  <p>{{ this.fanucDialogData.monitData.ShotCount }}</p>
                </el-row>
                <el-row style="font-weight: bold;font-size: large">
                  <p>良品数</p>
                </el-row>
                <el-row style="font-family: 'led regular';font-size: xxx-large;color: green">
                  <p>{{ this.fanucDialogData.monitData.GoodCount }}</p>
                </el-row>
                <el-row style="font-weight: bold;font-size: large">
                  <p>良率（%）</p>
                </el-row>
                <el-row style="font-family: 'led regular';font-size: xxx-large;color: green">
                  <p>{{
                      Math.round(this.fanucDialogData.monitData.GoodCount / this.fanucDialogData.monitData.ShotCount * 10000) / 100.00
                    }}%</p>
                </el-row>
              </el-col>
            </el-row>
            <el-row>
              <div style="font-weight: bold;color: white;background-color: #2d8cf0;width: 100%">设备状态</div>
            </el-row>
            <el-row>
              <div id='pieChart'
                   style="width: 98%;
                     height: 300px;
                     background-color: white;
                     margin-top: 10px">
              </div>
            </el-row>
          </el-col>
          <el-col :span="12">
            <el-tabs type="border-card" @tab-click="onDialogClick">
              <el-tab-pane label="成型条件">
                <div class="block">
                  <el-date-picker
                      v-model="dateTimePickerValue"
                      type="datetimerange"
                      :shortcuts="shortcuts"
                      range-separator="至"
                      start-placeholder="开始日期"
                      end-placeholder="结束日期"
                      size="small">
                  </el-date-picker>
                  <el-button type="primary" icon="el-icon-search" size="small" style="margin-left: 10px" @click="getDialogMachineCondData">查询</el-button>
                  <el-button type="success" icon="el-icon-download" size="small">导出</el-button>
                </div>
                <el-table
                    :data="fanucDialogCondData"
                    border
                    height=600px
                    header-row-class-name="tableHead"
                    style="width: 100%;margin-top: 10px">
                  <el-table-column  :width="80" prop="monitMcName" label="机台号"></el-table-column>
                  <el-table-column  :width="20 * '机台ID'.length" prop="monitMcId" label="机台ID"></el-table-column>
                  <el-table-column  :width="150" prop="condMoldFileName" label="项目号"></el-table-column>
                  <el-table-column  :width="20 * '模具ID'.length" prop="condMoldId" label="模具ID"></el-table-column>
                  <el-table-column  :width="20 * '保压段数'.length" prop="condPackStep" label="保压段数"></el-table-column>
                  <el-table-column  :width="20 * '保压1'.length" prop="condPackPres1" label="保压1"></el-table-column>
                  <el-table-column  :width="20 * '保压2'.length" prop="condPackPres2" label="保压2"></el-table-column>
                  <el-table-column  :width="20 * '保压3'.length" prop="condPackPres3" label="保压3"></el-table-column>
                  <el-table-column  :width="20 * '保压4'.length" prop="condPackPres4" label="保压4"></el-table-column>
                  <el-table-column  :width="20 * '保压5'.length" prop="condPackPres5" label="保压5"></el-table-column>
                  <el-table-column  :width="20 * '保压6'.length" prop="condPackPres6" label="保压6"></el-table-column>
                  <el-table-column  :width="20 * '计量前压力'.length" prop="condBefExtPres" label="计量前压力"></el-table-column>
                  <el-table-column  :width="20 * '保压时间1'.length" prop="condPackTime1" label="保压时间1"></el-table-column>
                  <el-table-column  :width="20 * '保压时间2'.length" prop="condPackTime2" label="保压时间2"></el-table-column>
                  <el-table-column  :width="20 * '保压时间3'.length" prop="condPackTime3" label="保压时间3"></el-table-column>
                  <el-table-column  :width="20 * '保压时间4'.length" prop="condPackTime4" label="保压时间4"></el-table-column>
                  <el-table-column  :width="20 * '保压时间5'.length" prop="condPackTime5" label="保压时间5"></el-table-column>
                  <el-table-column  :width="20 * '保压时间6'.length" prop="condPackTime6" label="保压时间6"></el-table-column>
                  <el-table-column  :width="20 * '计量前保压时间'.length" prop="condBefExtTime" label="计量前保压时间"></el-table-column>
                  <el-table-column  :width="20 * '最大射出压力'.length" prop="condMaxInjPres" label="最大射出压力"></el-table-column>
                  <el-table-column  :width="20 * '最大射出时间'.length" prop="condMaxInjTime" label="最大射出时间"></el-table-column>
                  <el-table-column  :width="20 * '最大保压速度'.length" prop="condMaxPackVel" label="最大保压速度"></el-table-column>
                  <el-table-column  :width="20 * '加速度时间'.length" prop="condAccelTime" label="加速度时间"></el-table-column>
                  <el-table-column  :width="20 * '加速度'.length" prop="condAcceleration" label="加速度"></el-table-column>
                  <el-table-column  :width="20 * '计量位置'.length" prop="condShotSize" label="计量位置"></el-table-column>
                  <el-table-column  :width="20 * '射出段数'.length" prop="condInjStep" label="射出段数"></el-table-column>
                  <el-table-column  :width="20 * '射出速度1'.length" prop="condInjSpeed1" label="射出速度1"></el-table-column>
                  <el-table-column  :width="20 * '射出速度2'.length" prop="condInjSpeed2" label="射出速度2"></el-table-column>
                  <el-table-column  :width="20 * '射出速度3'.length" prop="condInjSpeed3" label="射出速度3"></el-table-column>
                  <el-table-column  :width="20 * '射出速度4'.length" prop="condInjSpeed4" label="射出速度4"></el-table-column>
                  <el-table-column  :width="20 * '射出速度5'.length" prop="condInjSpeed5" label="射出速度5"></el-table-column>
                  <el-table-column  :width="20 * '射出速度6'.length" prop="condInjSpeed6" label="射出速度6"></el-table-column>
                  <el-table-column  :width="20 * '通常清料背压'.length" prop="condPurgePressure" label="通常清料背压"></el-table-column>
                  <el-table-column  :width="20 * '通常清料转速'.length" prop="condPurgeRotation" label="通常清料转速"></el-table-column>
                  <el-table-column  :width="20 * '自动模厚锁模力'.length" prop="condAutoDieHForce" label="自动模厚锁模力"></el-table-column>
                  <el-table-column  :width="20 * '射出速度7'.length" prop="condInjSpeed7" label="射出速度7"></el-table-column>
                  <el-table-column  :width="20 * '射出速度8'.length" prop="condInjSpeed8" label="射出速度8"></el-table-column>
                  <el-table-column  :width="20 * '射出速度9'.length" prop="condInjSpeed9" label="射出速度9"></el-table-column>
                  <el-table-column  :width="20 * '射出速度10'.length" prop="condInjSpeed10" label="射出速度10"></el-table-column>
                  <el-table-column  :width="20 * '最大射出压1'.length" prop="condMaxInjectPress1" label="最大射出压1"></el-table-column>
                  <el-table-column  :width="20 * '最大射出压2'.length" prop="condMaxInjectPress2" label="最大射出压2"></el-table-column>
                  <el-table-column  :width="20 * '最大射出压3'.length" prop="condMaxInjectPress3" label="最大射出压3"></el-table-column>
                  <el-table-column  :width="20 * '最大射出压4'.length" prop="condMaxInjectPress4" label="最大射出压4"></el-table-column>
                  <el-table-column  :width="20 * '最大射出压5'.length" prop="condMaxInjectPress5" label="最大射出压5"></el-table-column>
                  <el-table-column  :width="20 * '最大射出压6'.length" prop="condMaxInjectPress6" label="最大射出压6"></el-table-column>
                  <el-table-column  :width="20 * '最大射出压7'.length" prop="condMaxInjectPress7" label="最大射出压7"></el-table-column>
                  <el-table-column  :width="20 * '最大射出压8'.length" prop="condMaxInjectPress8" label="最大射出压8"></el-table-column>
                  <el-table-column  :width="20 * '最大射出压9'.length" prop="condMaxInjectPress9" label="最大射出压9"></el-table-column>
                  <el-table-column  :width="20 * '最大射出压10'.length" prop="condMaxInjectPress10" label="最大射出压10"></el-table-column>
                  <el-table-column  :width="20 * '射出切换位置1'.length" prop="condInjSwitchPos1" label="射出切换位置1"></el-table-column>
                  <el-table-column  :width="20 * '射出切换位置2'.length" prop="condInjSwitchPos2" label="射出切换位置2"></el-table-column>
                  <el-table-column  :width="20 * '射出切换位置3'.length" prop="condInjSwitchPos3" label="射出切换位置3"></el-table-column>
                  <el-table-column  :width="20 * '射出切换位置4'.length" prop="condInjSwitchPos4" label="射出切换位置4"></el-table-column>
                  <el-table-column  :width="20 * '射出切换位置5'.length" prop="condInjSwitchPos5" label="射出切换位置5"></el-table-column>
                  <el-table-column  :width="20 * '射出切换位置6'.length" prop="condInjSwitchPos6" label="射出切换位置6"></el-table-column>
                  <el-table-column  :width="20 * '射出切换位置7'.length" prop="condInjSwitchPos7" label="射出切换位置7"></el-table-column>
                  <el-table-column  :width="20 * '射出切换位置8'.length" prop="condInjSwitchPos8" label="射出切换位置8"></el-table-column>
                  <el-table-column  :width="20 * '射出切换位置9'.length" prop="condInjSwitchPos9" label="射出切换位置9"></el-table-column>
                  <el-table-column  :width="20 * '射出控制模式'.length" prop="condInjectMode" label="射出控制模式"></el-table-column>
                  <el-table-column  :width="20 * '切换位置'.length" prop="condTransPosition" label="切换位置"></el-table-column>
                  <el-table-column  :width="20 * '切换压力'.length" prop="condTransPressure" label="切换压力"></el-table-column>
                  <el-table-column  :width="20 * '切换压力段数'.length" prop="condTransPressStep" label="切换压力段数"></el-table-column>
                  <el-table-column  :width="20 * '切换模内/喷嘴压力'.length" prop="condTransCavNzlPrs" label="切换模内/喷嘴压力"></el-table-column>
                  <el-table-column  :width="20 * '切换模内压/NZ压段数'.length" prop="condTransCavityStep" label="切换模内压/NZ压段数"></el-table-column>
                  <el-table-column  :width="20 * '切换喷嘴压力'.length" prop="condTransNozzlePrs" label="切换喷嘴压力"></el-table-column>
                  <el-table-column  :width="20 * '切换喷嘴压力段数'.length" prop="condTransNozzleStep" label="切换喷嘴压力段数"></el-table-column>
                  <el-table-column  :width="20 * '信号切换段数'.length" prop="condSgnlTransfStep" label="信号切换段数"></el-table-column>
                  <el-table-column  :width="20 * '计量段数'.length" prop="condExtrdStep" label="计量段数"></el-table-column>
                  <el-table-column  :width="20 * '背压1'.length" prop="condBackPres1" label="背压1"></el-table-column>
                  <el-table-column  :width="20 * '背压2'.length" prop="condBackPres2" label="背压2"></el-table-column>
                  <el-table-column  :width="20 * '背压3'.length" prop="condBackPres3" label="背压3"></el-table-column>
                  <el-table-column  :width="20 * '背压4'.length" prop="condBackPres4" label="背压4"></el-table-column>
                  <el-table-column  :width="20 * '背压5'.length" prop="condBackPres5" label="背压5"></el-table-column>
                  <el-table-column  :width="20 * '背压6'.length" prop="condBackPres6" label="背压6"></el-table-column>
                  <el-table-column  :width="20 * '螺杆转速1'.length" prop="condScrewRotate1" label="螺杆转速1"></el-table-column>
                  <el-table-column  :width="20 * '螺杆转速2'.length" prop="condScrewRotate2" label="螺杆转速2"></el-table-column>
                  <el-table-column  :width="20 * '螺杆转速3'.length" prop="condScrewRotate3" label="螺杆转速3"></el-table-column>
                  <el-table-column  :width="20 * '螺杆转速4'.length" prop="condScrewRotate4" label="螺杆转速4"></el-table-column>
                  <el-table-column  :width="20 * '螺杆转速5'.length" prop="condScrewRotate5" label="螺杆转速5"></el-table-column>
                  <el-table-column  :width="20 * '螺杆转速6'.length" prop="condScrewRotate6" label="螺杆转速6"></el-table-column>
                  <el-table-column  :width="20 * '料筒保持温度'.length" prop="condNzl1HoldTemp" label="料筒保持温度"></el-table-column>
                  <el-table-column  :width="20 * '喷嘴2保持温度'.length" prop="condNzl2HoldTemp" label="喷嘴2保持温度"></el-table-column>
                  <el-table-column  :width="20 * '喷嘴连接器保持温度'.length" prop="condNzlAdaptHoldTemp" label="喷嘴连接器保持温度"></el-table-column>
                  <el-table-column  :width="20 * '料筒1保持温度'.length" prop="condBrl1HoldTemp" label="料筒1保持温度"></el-table-column>
                  <el-table-column  :width="20 * '料筒2保持温度'.length" prop="condBrl2HoldTemp" label="料筒2保持温度"></el-table-column>
                  <el-table-column  :width="20 * '料筒3保持温度'.length" prop="condBrl3HoldTemp" label="料筒3保持温度"></el-table-column>
                  <el-table-column  :width="20 * '料筒4保持温度'.length" prop="condBrl4HoldTemp" label="料筒4保持温度"></el-table-column>
                  <el-table-column  :width="20 * '料筒5保持温度'.length" prop="condBrl5HoldTemp" label="料筒5保持温度"></el-table-column>
                  <el-table-column  :width="20 * '料筒6保持温度'.length" prop="condBrl6HoldTemp" label="料筒6保持温度"></el-table-column>
                  <el-table-column  :width="20 * '喷嘴1设定温度'.length" prop="condNozzle1Set" label="喷嘴1设定温度"></el-table-column>
                  <el-table-column  :width="20 * '料筒1设定温度'.length" prop="condBarrel1Set" label="料筒1设定温度"></el-table-column>
                  <el-table-column  :width="20 * '料筒2设定温度'.length" prop="condBarrel2Set" label="料筒2设定温度"></el-table-column>
                  <el-table-column  :width="20 * '料筒3设定温度'.length" prop="condBarrel3Set" label="料筒3设定温度"></el-table-column>
                  <el-table-column  :width="20 * '料筒4设定温度'.length" prop="condBarrel4Set" label="料筒4设定温度"></el-table-column>
                  <el-table-column  :width="20 * '料筒5设定温度'.length" prop="condBarrel5Set" label="料筒5设定温度"></el-table-column>
                  <el-table-column  :width="20 * '料筒6设定温度'.length" prop="condBarrel6Set" label="料筒6设定温度"></el-table-column>
                  <el-table-column  :width="20 * '模具1设定温度'.length" prop="condMold1Set" label="模具1设定温度"></el-table-column>
                  <el-table-column  :width="20 * '模具2设定温度'.length" prop="condMold2Set" label="模具2设定温度"></el-table-column>
                  <el-table-column  :width="20 * '顶出开始模式:模具'.length" prop="condEjectStartModeMold" label="顶出开始模式:模具"></el-table-column>
                  <el-table-column  :width="20 * '顶出开始位置:模具'.length" prop="condEjectStartPosMold" label="顶出开始位置:模具"></el-table-column>
                  <el-table-column  :width="20 * '加速度模式'.length" prop="condAccelMode" label="加速度模式"></el-table-column>
                  <el-table-column  :width="20 * '计量模式'.length" prop="condExtrdSw" label="计量模式"></el-table-column>
                  <el-table-column  :width="20 * '计量切换位置1'.length" prop="condExtrdSwPos1" label="计量切换位置1"></el-table-column>
                  <el-table-column  :width="20 * '计量切换位置2'.length" prop="condExtrdSwPos2" label="计量切换位置2"></el-table-column>
                  <el-table-column  :width="20 * '计量切换位置3'.length" prop="condExtrdSwPos3" label="计量切换位置3"></el-table-column>
                  <el-table-column  :width="20 * '计量切换位置4'.length" prop="condExtrdSwPos4" label="计量切换位置4"></el-table-column>
                  <el-table-column  :width="20 * '计量切换位置5'.length" prop="condExtrdSwPos5" label="计量切换位置5"></el-table-column>
                  <el-table-column  :width="20 * '减压距离'.length" prop="condDcmpDist" label="减压距离"></el-table-column>
                  <el-table-column  :width="20 * '减压速度'.length" prop="condDcmpVel" label="减压速度"></el-table-column>
                  <el-table-column  :width="20 * '冷却时间'.length" prop="condCoolTime1" label="冷却时间"></el-table-column>
                  <el-table-column  :width="20 * '冷却时间'.length" prop="condCoolTime2" label="冷却时间"></el-table-column>
                  <el-table-column  :width="20 * '喷嘴1上限温度'.length" prop="condNozzle1High" label="喷嘴1上限温度"></el-table-column>
                  <el-table-column  :width="20 * '料筒1上限温度'.length" prop="condBarrel1High" label="料筒1上限温度"></el-table-column>
                  <el-table-column  :width="20 * '料筒2上限温度'.length" prop="condBarrel2High" label="料筒2上限温度"></el-table-column>
                  <el-table-column  :width="20 * '料筒3上限温度'.length" prop="condBarrel3High" label="料筒3上限温度"></el-table-column>
                  <el-table-column  :width="20 * '料斗下上限温度'.length" prop="condFeedThroatHigh" label="料斗下上限温度"></el-table-column>
                  <el-table-column  :width="20 * '模具1上限温度'.length" prop="condMold1High" label="模具1上限温度"></el-table-column>
                  <el-table-column  :width="20 * '模具2上限温度'.length" prop="condMold2High" label="模具2上限温度"></el-table-column>
                  <el-table-column  :width="20 * '模具3上限温度'.length" prop="condMold3High" label="模具3上限温度"></el-table-column>
                  <el-table-column  :width="20 * '模具4上限温度'.length" prop="condMold4High" label="模具4上限温度"></el-table-column>
                  <el-table-column  :width="20 * '料斗下设定温度'.length" prop="condFeedThroatSet" label="料斗下设定温度"></el-table-column>
                  <el-table-column  :width="20 * '模具3设定温度'.length" prop="condMold3Set" label="模具3设定温度"></el-table-column>
                  <el-table-column  :width="20 * '模具4设定温度'.length" prop="condMold4Set" label="模具4设定温度"></el-table-column>
                  <el-table-column  :width="20 * '喷嘴1下限温度'.length" prop="condNozzle1Low" label="喷嘴1下限温度"></el-table-column>
                  <el-table-column  :width="20 * '料筒1下限温度'.length" prop="condBarrel1Low" label="料筒1下限温度"></el-table-column>
                  <el-table-column  :width="20 * '料筒2下限温度'.length" prop="condBarrel2Low" label="料筒2下限温度"></el-table-column>
                  <el-table-column  :width="20 * '料筒3下限温度'.length" prop="condBarrel3Low" label="料筒3下限温度"></el-table-column>
                  <el-table-column  :width="20 * '料斗下下限温度'.length" prop="condFeedThroatLow" label="料斗下下限温度"></el-table-column>
                  <el-table-column  :width="20 * '模具1下限温度'.length" prop="condMold1Low" label="模具1下限温度"></el-table-column>
                  <el-table-column  :width="20 * '模具2下限温度'.length" prop="condMold2Low" label="模具2下限温度"></el-table-column>
                  <el-table-column  :width="20 * '模具3下限温度'.length" prop="condMold3Low" label="模具3下限温度"></el-table-column>
                  <el-table-column  :width="20 * '模具4下限温度'.length" prop="condMold4Low" label="模具4下限温度"></el-table-column>
                  <el-table-column  :width="20 * '闭模限'.length" prop="condCloseLimitPos" label="闭模限"></el-table-column>
                  <el-table-column  :width="20 * '闭模变速位置1'.length" prop="condCloseSwPos1" label="闭模变速位置1"></el-table-column>
                  <el-table-column  :width="20 * '闭模变速位置2'.length" prop="condCloseSwPos2" label="闭模变速位置2"></el-table-column>
                  <el-table-column  :width="20 * '闭模变速位置3'.length" prop="condCloseSwPos3" label="闭模变速位置3"></el-table-column>
                  <el-table-column  :width="20 * '模具保护位置'.length" prop="condMoldProtect" label="模具保护位置"></el-table-column>
                  <el-table-column  :width="20 * '模具接触位置'.length" prop="condMoldTouchPos" label="模具接触位置"></el-table-column>
                  <el-table-column  :width="20 * '开模变速位置1'.length" prop="condOpenSwPos1" label="开模变速位置1"></el-table-column>
                  <el-table-column  :width="20 * '开模变速位置2'.length" prop="condOpenSwPos2" label="开模变速位置2"></el-table-column>
                  <el-table-column  :width="20 * '开模变速位置3'.length" prop="condOpenSwPos3" label="开模变速位置3"></el-table-column>
                  <el-table-column  :width="20 * '开模变速位置4'.length" prop="condOpenSwPos4" label="开模变速位置4"></el-table-column>
                  <el-table-column  :width="20 * '开模结束位置'.length" prop="condFullyOpenPos" label="开模结束位置"></el-table-column>
                  <el-table-column  :width="20 * '顶出开始位置'.length" prop="condEjectStartPos" label="顶出开始位置"></el-table-column>
                  <el-table-column  :width="20 * '闭模速度1'.length" prop="condCloseVel1" label="闭模速度1"></el-table-column>
                  <el-table-column  :width="20 * '闭模速度2'.length" prop="condCloseVel2" label="闭模速度2"></el-table-column>
                  <el-table-column  :width="20 * '闭模速度3'.length" prop="condCloseVel3" label="闭模速度3"></el-table-column>
                  <el-table-column  :width="20 * '闭模速度4'.length" prop="condCloseVel4" label="闭模速度4"></el-table-column>
                  <el-table-column  :width="20 * '模具接触速度'.length" prop="condMoldTouchVel" label="模具接触速度"></el-table-column>
                  <el-table-column  :width="20 * '开模速度1'.length" prop="condOpenVel1" label="开模速度1"></el-table-column>
                  <el-table-column  :width="20 * '开模速度2'.length" prop="condOpenVel2" label="开模速度2"></el-table-column>
                  <el-table-column  :width="20 * '开模速度3'.length" prop="condOpenVel3" label="开模速度3"></el-table-column>
                  <el-table-column  :width="20 * '开模速度4'.length" prop="condOpenVel4" label="开模速度4"></el-table-column>
                  <el-table-column  :width="20 * '开模结束速度'.length" prop="condFullyOpenVel" label="开模结束速度"></el-table-column>
                  <el-table-column  :width="20 * '闭模段数'.length" prop="condCloseStep" label="闭模段数"></el-table-column>
                  <el-table-column  :width="20 * '开模段数'.length" prop="condOpenStep" label="开模段数"></el-table-column>
                  <el-table-column  :width="20 * '模具保护力1'.length" prop="condMoldProtect1" label="模具保护力1"></el-table-column>
                  <el-table-column  :width="20 * '模具保护力1(减)'.length" prop="condMoldProtect1Minus" label="模具保护力1(减)"></el-table-column>
                  <el-table-column  :width="20 * '模具保护力2'.length" prop="condMoldProtect2" label="模具保护力2"></el-table-column>
                  <el-table-column  :width="20 * '模具保护力2(减)'.length" prop="condMoldProtect2Minus" label="模具保护力2(减)"></el-table-column>
                  <el-table-column  :width="20 * '模具保护时间1'.length" prop="condProtectTime1" label="模具保护时间1"></el-table-column>
                  <el-table-column  :width="20 * '模具保护时间2'.length" prop="condProtectTime2" label="模具保护时间2"></el-table-column>
                  <el-table-column  :width="20 * '顶杆动作类型'.length" prop="condEjectPattern1" label="顶杆动作类型"></el-table-column>
                  <el-table-column  :width="20 * '顶出次数'.length" prop="condEjectPulse" label="顶出次数"></el-table-column>
                  <el-table-column  :width="20 * '顶出开始模式'.length" prop="condEjectStartMode" label="顶出开始模式"></el-table-column>
                  <el-table-column  :width="20 * '顶杆后退位置'.length" prop="condEjectRetractPos" label="顶杆后退位置"></el-table-column>
                  <el-table-column  :width="20 * '顶杆前进位置'.length" prop="condEjectFullyAdvance" label="顶杆前进位置"></el-table-column>
                  <el-table-column  :width="20 * '顶杆后退速度'.length" prop="condEjectRetractVel" label="顶杆后退速度"></el-table-column>
                  <el-table-column  :width="20 * '顶杆前进速度'.length" prop="condEjectAdvanceVel" label="顶杆前进速度"></el-table-column>
                  <el-table-column  :width="20 * '顶杆动作类型'.length" prop="condEjectPattern2" label="顶杆动作类型"></el-table-column>
                  <el-table-column  :width="20 * '顶杆后退停止'.length" prop="condEjectDwellInRet" label="顶杆后退停止"></el-table-column>
                  <el-table-column  :width="20 * '顶杆前进停止'.length" prop="condEjectDwellInAdv" label="顶杆前进停止"></el-table-column>
                  <el-table-column  :width="20 * 'HR模式'.length" prop="condHrMode" label="HR模式"></el-table-column>
                  <el-table-column  :width="20 * '切换模式'.length" prop="condTransMode" label="切换模式"></el-table-column>
                  <el-table-column  :width="20 * '工序监视项目05'.length" prop="condProcMoniItem5" label="工序监视项目05"></el-table-column>
                  <el-table-column fixed="right" :width="185" prop="dbCreateTime" label="插入时间"></el-table-column>
                </el-table>
              </el-tab-pane>
              <el-tab-pane label="监控数据">
                <div class="block">
                  <el-date-picker
                      v-model="dateTimePickerValue"
                      type="datetimerange"
                      :shortcuts="shortcuts"
                      range-separator="至"
                      start-placeholder="开始日期"
                      end-placeholder="结束日期"
                      size="small">
                  </el-date-picker>
                  <el-button type="primary" icon="el-icon-search" size="small" style="margin-left: 10px">查询</el-button>
                  <el-button type="success" icon="el-icon-download" size="small">导出</el-button>
                </div>
              </el-tab-pane>
              <el-tab-pane label="报警履历">
                <div class="block">
                  <el-date-picker
                      v-model="dateTimePickerValue"
                      type="datetimerange"
                      :shortcuts="shortcuts"
                      range-separator="至"
                      start-placeholder="开始日期"
                      end-placeholder="结束日期"
                      size="small">
                  </el-date-picker>
                  <el-button type="primary" icon="el-icon-search" size="small" style="margin-left: 10px">查询</el-button>
                  <el-button type="success" icon="el-icon-download" size="small">导出</el-button>
                </div>
              </el-tab-pane>
              <el-tab-pane label="数据分析">数据分析</el-tab-pane>
            </el-tabs>
          </el-col>
        </el-row>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import {getByFloor, getDetailInfo, getCondData} from "@/api/fanuc";

export default {
  name: "FanucMonitor",
  created() {
    this.getFanucDataByFloor()
    this.timer = setInterval(() => {
      this.getFanucDataByFloor()
    }, 10000)
  },
  computed: {
    monitPages() {
      const pages = []
      this.fanucMonitorInfo.forEach((item, index) => {
        const page = Math.floor(index / 4)//4代表4条为一行，随意更改
        if (!pages[page]) {
          pages[page] = []
        }
        pages[page].push(item)
      })
      return pages
    }
  },
  methods: {
    getFanucDataByFloor() {
      const position = this.$route.query.position;
      getByFloor(position.substring(2, position.length)).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          this.fanucMonitorInfo = responseData.data;
        }
      })
    },
    onDialogClick(){
      this.fanucDialogCondData = []
    },
    getFanucDetailData() {
      getDetailInfo(this.dialogMachineName).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          this.fanucDialogData = responseData.data;
        }
      })
    },
    checkMachineStatus(status) {
      return status !== '-1';
    },
    onCardClick(idx, innerIdx) {
      this.dialogMachineName = this.monitPages[idx][innerIdx].monitMcName
      this.getFanucDetailData()
      this.dialogVisible = true
    },
    onDialogClosed(){
      this.fanucDialogCondData = []
      this.dateTimePickerValue = []
    },
    getStatusType(status) {
      return Object.prototype.hasOwnProperty.call(this.statusType, status) ? this.statusType[status] : "item_otherStatus";
    },
    getStatusName(status) {
      return Object.prototype.hasOwnProperty.call(this.status, status) ? this.status[status] : "其他";
    },
    getDialogMachineCondData(){
      if(this.dateTimePickerValue.length !== 2){
        this.$message.error("请选择查询时间段");
      }else{
        const startTime = this.$moment(this.dateTimePickerValue[0]).format('YYYY-MM-DD HH:mm:ss');
        const endTime = this.$moment(this.dateTimePickerValue[1]).format('YYYY-MM-DD HH:mm:ss');
        getCondData(startTime, endTime, this.dialogMachineName).then((response) => {
          const responseData = response.data
          if (responseData.code === '000000') {
            this.fanucDialogCondData = responseData.data;
          }
        })
      }
    },
    showPieChart() {
      const chartDom = document.getElementById('pieChart');
      const myChart = echarts.init(chartDom);
      let option;
      option = {
        tooltip: {
          trigger: 'item',
          formatter: "{a} <br/>{b} : {c} %"
        },
        legend: {
          top: '5%',
          left: 'center'
        },
        series: [
          {
            name: '状态',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '28',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: this.getMoldData()
          }
        ]
      };
      option && myChart.setOption(option);
    },
    getMoldData() {
      const moldData = [];
      const mold_automate = this.fanucDialogData.moldData.mold_automate.replace('%', '').trim()
      const mold_wait = this.fanucDialogData.moldData.mold_wait.replace('%', '').trim()
      const mold_manual = this.fanucDialogData.moldData.mold_manual.replace('%', '').trim()
      const mold_alarm = this.fanucDialogData.moldData.mold_alarm.replace('%', '').trim()
      const mold_complete = this.fanucDialogData.moldData.mold_complete.replace('%', '').trim()
      const mold_shutdown = this.fanucDialogData.moldData.mold_shutdown.replace('%', '').trim()
      const mold_other = (100.00 - mold_automate - mold_wait - mold_manual - mold_alarm - mold_complete - mold_shutdown).toFixed(2)
      moldData.push({value: mold_automate, name: '自动运转'})
      moldData.push({value: mold_wait, name: '运转待机'})
      moldData.push({value: mold_manual, name: '手动运转'})
      moldData.push({value: mold_alarm, name: '报警'})
      moldData.push({value: mold_complete, name: '生产完成'})
      moldData.push({value: mold_shutdown, name: '停机'})
      moldData.push({value: mold_other, name: '其他'})
      return moldData;
    }
  },
  data() {
    return {
      shortcuts: [{
        text: '最近一周',
        value: (() => {
          const end = new Date();
          const start = new Date();
          start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
          return [start, end]
        })()
      }, {
        text: '最近一个月',
        value: (() => {
          const end = new Date();
          const start = new Date();
          start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
          return [start, end]
        })()
      }, {
        text: '最近三个月',
        value: (() => {
          const end = new Date();
          const start = new Date();
          start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
          return [start, end]
        })()
      }],
      dateTimePickerValue: [],
      dialogMachineName: '',
      dialogVisible: false,
      fanucMonitorInfo: [],
      fanucDialogData: {
        monitData: {},
        condData: {},
        moldData: {},
        moldFileName: ""
      },
      fanucDialogCondData: [],
      statusType: {
        '-1': 'item_disconnect',
        '00': 'item_manual',
        '01': 'item_wait',
        '02': 'item_automatic',
        '03': 'item_alarm',
        '17': 'item_hold',
        '50': 'item_semiAuto'
      },
      status: {
        '-1': '离线',
        '00': '手动运转',
        '01': '运转待机',
        '02': '自动运转',
        '03': '报警',
        '17': '低温保持',
        '50': '半自动'
      }

    }
  }
};
</script>
