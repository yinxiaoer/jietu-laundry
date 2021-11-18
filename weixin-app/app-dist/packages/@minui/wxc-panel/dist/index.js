'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = Component({
  behaviors: [],
  properties: {
    title: {
      type: String,
      value: ''
    },
    margin: {
      type: Boolean,
      value: true
    },
    border: {
      type: Boolean,
      value: true
    }
  },
  data: {},
  methods: {
    onClick: function onClick(event) {
      var detail = event.detail;
      var option = {};
      this.triggerEvent('click', detail, option);
    }
  }
});
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbImluZGV4Lnd4YyJdLCJuYW1lcyI6WyJiZWhhdmlvcnMiLCJwcm9wZXJ0aWVzIiwidGl0bGUiLCJ0eXBlIiwiU3RyaW5nIiwidmFsdWUiLCJtYXJnaW4iLCJCb29sZWFuIiwiYm9yZGVyIiwiZGF0YSIsIm1ldGhvZHMiLCJvbkNsaWNrIiwiZXZlbnQiLCJkZXRhaWwiLCJvcHRpb24iLCJ0cmlnZ2VyRXZlbnQiXSwibWFwcGluZ3MiOiI7Ozs7OztBQUlFQSxhQUFXLEU7QUFDWEMsY0FBWTtBQUNWQyxXQUFPO0FBQ0xDLFlBQU1DLE1BREQ7QUFFTEMsYUFBTztBQUZGLEtBREc7QUFLVkMsWUFBUTtBQUNOSCxZQUFNSSxPQURBO0FBRU5GLGFBQU87QUFGRCxLQUxFO0FBU1ZHLFlBQVE7QUFDTkwsWUFBTUksT0FEQTtBQUVORixhQUFPO0FBRkQ7QUFURSxHO0FBY1pJLFFBQU0sRTtBQUNOQyxXQUFTO0FBQ1BDLFdBRE8sbUJBQ0NDLEtBREQsRUFDUTtBQUNYLFVBQUlDLFNBQVNELE1BQU1DLE1BQW5CO0FBQ0EsVUFBSUMsU0FBUyxFQUFiO0FBQ0EsV0FBS0MsWUFBTCxDQUFrQixPQUFsQixFQUEyQkYsTUFBM0IsRUFBbUNDLE1BQW5DO0FBQ0g7QUFMTSIsImZpbGUiOiJpbmRleC53eGMiLCJzb3VyY2VzQ29udGVudCI6WyJleHBvcnQgZGVmYXVsdCB7XG4gIGNvbmZpZzoge1xuICAgIHVzaW5nQ29tcG9uZW50czogeyB9XG4gIH0sXG4gIGJlaGF2aW9yczogW10sXG4gIHByb3BlcnRpZXM6IHtcbiAgICB0aXRsZToge1xuICAgICAgdHlwZTogU3RyaW5nLFxuICAgICAgdmFsdWU6ICcnXG4gICAgfSxcbiAgICBtYXJnaW46IHtcbiAgICAgIHR5cGU6IEJvb2xlYW4sXG4gICAgICB2YWx1ZTogdHJ1ZVxuICAgIH0sXG4gICAgYm9yZGVyOiB7XG4gICAgICB0eXBlOiBCb29sZWFuLFxuICAgICAgdmFsdWU6IHRydWVcbiAgICB9XG4gIH0sXG4gIGRhdGE6IHsgfSxcbiAgbWV0aG9kczoge1xuICAgIG9uQ2xpY2soZXZlbnQpIHtcbiAgICAgICAgbGV0IGRldGFpbCA9IGV2ZW50LmRldGFpbDtcbiAgICAgICAgbGV0IG9wdGlvbiA9IHt9O1xuICAgICAgICB0aGlzLnRyaWdnZXJFdmVudCgnY2xpY2snLCBkZXRhaWwsIG9wdGlvbik7XG4gICAgfVxuICB9XG59Il19