import 'Frontend/generated/jar-resources/flow-component-renderer.js';
import '@vaadin/polymer-legacy-adapter/style-modules.js';
import '@vaadin/combo-box/theme/lumo/vaadin-combo-box.js';
import 'Frontend/generated/jar-resources/comboBoxConnector.js';
import '@vaadin/side-nav/theme/lumo/vaadin-side-nav.js';
import 'Frontend/generated/jar-resources/vaadin-grid-flow-selection-column.js';
import '@vaadin/grid/theme/lumo/vaadin-grid-column.js';
import '@vaadin/app-layout/theme/lumo/vaadin-app-layout.js';
import '@vaadin/tooltip/theme/lumo/vaadin-tooltip.js';
import '@vaadin/tabs/theme/lumo/vaadin-tab.js';
import '@vaadin/progress-bar/theme/lumo/vaadin-progress-bar.js';
import '@vaadin/button/theme/lumo/vaadin-button.js';
import 'Frontend/generated/jar-resources/buttonFunctions.js';
import '@vaadin/form-layout/theme/lumo/vaadin-form-layout.js';
import '@vaadin/dialog/theme/lumo/vaadin-dialog.js';
import '@vaadin/vertical-layout/theme/lumo/vaadin-vertical-layout.js';
import '@vaadin/horizontal-layout/theme/lumo/vaadin-horizontal-layout.js';
import '@vaadin/grid/theme/lumo/vaadin-grid-column-group.js';
import '@vaadin/icon/theme/lumo/vaadin-icon.js';
import '@vaadin/side-nav/theme/lumo/vaadin-side-nav-item.js';
import '@vaadin/context-menu/theme/lumo/vaadin-context-menu.js';
import 'Frontend/generated/jar-resources/contextMenuConnector.js';
import 'Frontend/generated/jar-resources/contextMenuTargetConnector.js';
import '@vaadin/form-layout/theme/lumo/vaadin-form-item.js';
import '@vaadin/multi-select-combo-box/theme/lumo/vaadin-multi-select-combo-box.js';
import '@vaadin/grid/theme/lumo/vaadin-grid.js';
import '@vaadin/grid/theme/lumo/vaadin-grid-sorter.js';
import '@vaadin/checkbox/theme/lumo/vaadin-checkbox.js';
import 'Frontend/generated/jar-resources/gridConnector.ts';
import '@vaadin/text-field/theme/lumo/vaadin-text-field.js';
import '@vaadin/icons/vaadin-iconset.js';
import '@vaadin/date-picker/theme/lumo/vaadin-date-picker.js';
import 'Frontend/generated/jar-resources/datepickerConnector.js';
import '@vaadin/text-area/theme/lumo/vaadin-text-area.js';
import '@vaadin/app-layout/theme/lumo/vaadin-drawer-toggle.js';
import '@vaadin/tabs/theme/lumo/vaadin-tabs.js';
import '@vaadin/avatar/theme/lumo/vaadin-avatar.js';
import 'Frontend/generated/jar-resources/lit-renderer.ts';
import '@vaadin/notification/theme/lumo/vaadin-notification.js';
import '@vaadin/password-field/theme/lumo/vaadin-password-field.js';
import '@vaadin/email-field/theme/lumo/vaadin-email-field.js';
import '@vaadin/common-frontend/ConnectionIndicator.js';
import '@vaadin/vaadin-lumo-styles/color-global.js';
import '@vaadin/vaadin-lumo-styles/typography-global.js';
import '@vaadin/vaadin-lumo-styles/sizing.js';
import '@vaadin/vaadin-lumo-styles/spacing.js';
import '@vaadin/vaadin-lumo-styles/style.js';
import '@vaadin/vaadin-lumo-styles/vaadin-iconset.js';

const loadOnDemand = (key) => {
  const pending = [];
  if (key === '72c49b90cbf005349c09796c6c9001ac91fbd388746db9f686662e07a298ecb6') {
    pending.push(import('./chunks/chunk-450bfcdef4b02507f560cdcb4374f556b98d350e7ca57d02730f99cc109826da.js'));
  }
  if (key === '79b3b377f91e47a5332620af24915435210cc343fe5f714febf6d4defbef11a2') {
    pending.push(import('./chunks/chunk-de06cf4e28ddf0e1054b77ecd47f35e39ef08eaea32dd6fc302d84d4f5a502a4.js'));
  }
  if (key === 'bacd8c22fb5ffdc95e7f6eb0514f5d94246cfa0266e1125c81e7a370c4f0b145') {
    pending.push(import('./chunks/chunk-de06cf4e28ddf0e1054b77ecd47f35e39ef08eaea32dd6fc302d84d4f5a502a4.js'));
  }
  if (key === '155e81e0066553029260cd2261784c60503e28f0ec1f2236e55f8c8bbc46a5f1') {
    pending.push(import('./chunks/chunk-bcb176b0ab5e4f393fa1f72aa98a90bfe44b505d07abf990dbaf6b8a4937206f.js'));
  }
  if (key === 'b3aa8ab765630dc5baea119a3f40bf3b642019f24d21bbaf6509037e6f2ff67f') {
    pending.push(import('./chunks/chunk-db71c6bde9e497ec91694294d47037932e31f14c6638f15f3c7b839d15b5c7f2.js'));
  }
  if (key === 'c75411d9d85bef87ffe9f0249e6023dc10c0d01bf227cb16c80bfa4594ed2e2e') {
    pending.push(import('./chunks/chunk-de06cf4e28ddf0e1054b77ecd47f35e39ef08eaea32dd6fc302d84d4f5a502a4.js'));
  }
  if (key === 'f7c24fb0f0e9f3453b386200fc4cfc16c87d48246042a97316b65d3461e384a9') {
    pending.push(import('./chunks/chunk-4bd08779ac35fb489afacd99e510b291d31249a9e6926a2d7017f01c29c1ffb0.js'));
  }
  if (key === '808956df651cb09ddc15bdbca61d119e2d0007e3e9b006bd3c40e8e9440f0246') {
    pending.push(import('./chunks/chunk-291afa9f900cf9e574f33faecc2e8a73f5404765545ee2470786cc7aafcc137f.js'));
  }
  if (key === 'c58b1e23cca3ef8314f9d787ebb2c270bb47f9ac97990bb3e4e6de142b10706f') {
    pending.push(import('./chunks/chunk-de06cf4e28ddf0e1054b77ecd47f35e39ef08eaea32dd6fc302d84d4f5a502a4.js'));
  }
  return Promise.all(pending);
}

window.Vaadin = window.Vaadin || {};
window.Vaadin.Flow = window.Vaadin.Flow || {};
window.Vaadin.Flow.loadOnDemand = loadOnDemand;
window.Vaadin.Flow.resetFocus = () => {
 let ae=document.activeElement;
 while(ae&&ae.shadowRoot) ae = ae.shadowRoot.activeElement;
 return !ae || ae.blur() || ae.focus() || true;
}