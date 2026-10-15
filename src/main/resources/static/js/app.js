/* Taxis V2 — interactions front (Option A) */
(function () {
  'use strict';

  /* ---------- 1. Thème clair / sombre ---------- */
  const THEME_KEY = 'taxis-theme';
  const root = document.documentElement;

  function applyTheme(theme) {
    if (theme === 'dark') root.setAttribute('data-theme', 'dark');
    else root.removeAttribute('data-theme');
    const btn = document.getElementById('themeToggle');
    if (btn) btn.innerHTML = theme === 'dark'
      ? '<i class="bi bi-sun"></i>'
      : '<i class="bi bi-moon-stars"></i>';
  }

  const stored = localStorage.getItem(THEME_KEY);
  const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
  applyTheme(stored || (prefersDark ? 'dark' : 'light'));

  document.addEventListener('click', function (e) {
    const btn = e.target.closest('#themeToggle');
    if (!btn) return;
    const next = root.getAttribute('data-theme') === 'dark' ? 'light' : 'dark';
    localStorage.setItem(THEME_KEY, next);
    applyTheme(next);
  });

  /* ---------- 2. Sidebar mobile ---------- */
  const sidebar  = document.getElementById('sidebar');
  const toggle   = document.getElementById('sidebarToggle');
  const backdrop = document.getElementById('backdrop');

  function closeSidebar() {
    if (sidebar) sidebar.classList.remove('open');
    if (backdrop) backdrop.classList.remove('show');
  }
  function openSidebar() {
    if (sidebar) sidebar.classList.add('open');
    if (backdrop) backdrop.classList.add('show');
  }

  if (toggle) toggle.addEventListener('click', function () {
    if (sidebar && sidebar.classList.contains('open')) closeSidebar();
    else openSidebar();
  });
  if (backdrop) backdrop.addEventListener('click', closeSidebar);
  window.addEventListener('keydown', function (e) {
    if (e.key === 'Escape') closeSidebar();
  });

  /* ---------- 3. Lien actif dans la sidebar ---------- */
  const path = window.location.pathname;
  document.querySelectorAll('.sidebar__link').forEach(function (a) {
    const href = a.getAttribute('href') || '';
    if (href === '/' && path === '/') a.classList.add('active');
    else if (href !== '/' && href !== '' && path.startsWith(href)) a.classList.add('active');
  });

  /* ---------- 4. Auto-dismiss des messages ---------- */
  document.querySelectorAll('.message').forEach(function (msg) {
    setTimeout(function () {
      msg.style.transition = 'opacity .3s, transform .3s';
      msg.style.opacity = '0';
      msg.style.transform = 'translateY(-6px)';
      setTimeout(function () { msg.remove(); }, 320);
    }, 5000);
  });

  /* ---------- 5. Statuts → badges automatiques ---------- */
  const STATUS_MAP = {
    EN_ATTENTE: 'badge--warning',
    ASSIGNEE:   'badge--info',
    EN_COURS:   'badge--brand',
    TERMINEE:   'badge--success',
    ANNULEE:    'badge--danger',
    DISPONIBLE:   'badge--success',
    EN_PAUSE:     'badge--warning',
    INDISPONIBLE: 'badge--danger',
    SUSPENDU:     'badge--danger',
    MAINTENANCE:  'badge--warning',
    HORS_SERVICE: 'badge--danger'
  };

  document.querySelectorAll('tbody td span, tbody td').forEach(function (el) {
    if (el.children.length > 0) return;
    const txt = (el.textContent || '').trim();
    if (!txt || !STATUS_MAP[txt]) return;
    const b = document.createElement('span');
    b.className = 'badge ' + STATUS_MAP[txt];
    b.textContent = txt.replace(/_/g, ' ').toLowerCase();
    el.textContent = '';
    el.appendChild(b);
  });
})();