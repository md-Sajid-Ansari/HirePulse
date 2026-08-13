import { renderJobPortal } from './components/renderJobs.js';
import { renderDSASheet } from './components/renderDSA.js';
import { renderPrepHub } from './components/renderPrep.js';
import { renderCompanyGuide } from './components/renderCompany.js';
import { renderApplicationTracker } from './components/renderTracker.js';
import { getSolvedDSAIds, getAppliedJobs, getStorageItem, setStorageItem } from './utils/storage.js';
import { dsaProblems } from './data/dsaSheetData.js';
import { getIconSvg } from './utils/icons.js';

let activeTab = 'job-portal';

document.addEventListener('DOMContentLoaded', () => {
  initTheme();
  updateNavHeaderCounters();
  bindNavigation();
  renderActiveTab();
});

const initTheme = () => {
  const savedTheme = getStorageItem('hp_theme', 'dark');
  document.documentElement.setAttribute('data-theme', savedTheme);

  const toggleBtn = document.getElementById('themeToggleBtn');
  if (toggleBtn) {
    toggleBtn.innerHTML = savedTheme === 'dark' ? getIconSvg('sun', 18) : getIconSvg('moon', 18);
    toggleBtn.addEventListener('click', () => {
      const current = document.documentElement.getAttribute('data-theme');
      const nextTheme = current === 'dark' ? 'light' : 'dark';
      document.documentElement.setAttribute('data-theme', nextTheme);
      setStorageItem('hp_theme', nextTheme);
      toggleBtn.innerHTML = nextTheme === 'dark' ? getIconSvg('sun', 18) : getIconSvg('moon', 18);
    });
  }
};

const updateNavHeaderCounters = () => {
  const solvedIds = getSolvedDSAIds();
  const appliedJobs = getAppliedJobs();
  const totalDsa = dsaProblems.length;
  const dsaPct = totalDsa > 0 ? Math.round((solvedIds.length / totalDsa) * 100) : 0;

  const dsaCountEl = document.getElementById('navDsaCount');
  const appCountEl = document.getElementById('navAppCount');
  const headerDsaPctEl = document.getElementById('headerDsaPct');

  if (dsaCountEl) dsaCountEl.textContent = `${solvedIds.length}/${totalDsa}`;
  if (appCountEl) appCountEl.textContent = `${appliedJobs.length}`;
  if (headerDsaPctEl) headerDsaPctEl.textContent = `${dsaPct}% Solved`;
};

const bindNavigation = () => {
  document.querySelectorAll('.nav-tab-btn').forEach(btn => {
    btn.addEventListener('click', () => {
      const targetTab = btn.dataset.tab;
      switchTab(targetTab);
    });
  });

  document.getElementById('brandLogo')?.addEventListener('click', (e) => {
    e.preventDefault();
    switchTab('job-portal');
  });
};

const switchTab = (tabName) => {
  activeTab = tabName;
  document.querySelectorAll('.nav-tab-btn').forEach(btn => {
    if (btn.dataset.tab === tabName) btn.classList.add('active');
    else btn.classList.remove('active');
  });
  renderActiveTab();
};

const renderActiveTab = () => {
  const mainEl = document.getElementById('mainContent');
  updateNavHeaderCounters();

  switch (activeTab) {
    case 'job-portal':
      renderJobPortal(mainEl, openGlobalModal);
      break;
    case 'dsa-sheet':
      renderDSASheet(mainEl, openGlobalModal, updateNavHeaderCounters);
      break;
    case 'prep-hub':
      renderPrepHub(mainEl, openGlobalModal);
      break;
    case 'company-guide':
      renderCompanyGuide(mainEl, openGlobalModal);
      break;
    case 'my-tracker':
      renderApplicationTracker(mainEl, openGlobalModal, switchTab);
      break;
    default:
      renderJobPortal(mainEl, openGlobalModal);
  }
};

// Global Modal Handler
export const openGlobalModal = ({ title, body, footer = '' }) => {
  const modalTarget = document.getElementById('modalTarget');
  
  const modalHtml = `
    <div class="modal-overlay" id="globalModalOverlay">
      <div class="modal-container">
        <div class="modal-header">
          <div class="modal-title">${title}</div>
          <button class="modal-close" id="btnCloseGlobalModal">
            ${getIconSvg('x', 20)}
          </button>
        </div>
        <div class="modal-body">
          ${body}
        </div>
        ${footer ? `<div class="modal-footer">${footer}</div>` : ''}
      </div>
    </div>
  `;

  modalTarget.innerHTML = modalHtml;

  const closeFn = () => {
    modalTarget.innerHTML = '';
  };

  document.getElementById('btnCloseGlobalModal')?.addEventListener('click', closeFn);
  document.getElementById('globalModalOverlay')?.addEventListener('click', (e) => {
    if (e.target.id === 'globalModalOverlay') closeFn();
  });
};

