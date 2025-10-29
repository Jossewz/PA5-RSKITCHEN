(function() {

  const cursor = document.querySelector('.custom-cursor');
  const follower = document.querySelector('.cursor-follower');

  if (cursor && follower) {
    document.addEventListener('mousemove', (e) => {
      cursor.style.left = e.clientX + 'px';
      cursor.style.top = e.clientY + 'px';

      setTimeout(() => {
        follower.style.left = e.clientX + 'px';
        follower.style.top = e.clientY + 'px';
      }, 100);
    });

    document.addEventListener('mousedown', () => {
      cursor.style.transform = 'scale(0.8)';
      follower.style.transform = 'scale(1.2)';
    });

    document.addEventListener('mouseup', () => {
      cursor.style.transform = 'scale(1)';
      follower.style.transform = 'scale(1)';
    });
  }

  const progressBar = document.querySelector('.scroll-progress');
  if (progressBar) {
    window.addEventListener('scroll', () => {
      const totalHeight = document.documentElement.scrollHeight - window.innerHeight;
      const progress = totalHeight > 0 ? (window.pageYOffset / totalHeight) * 100 : 0;
      progressBar.style.width = progress + '%';

      const navbar = document.querySelector('.menu-bar');
      if (navbar) {
        if (window.scrollY > 100) navbar.classList.add('scrolled');
        else navbar.classList.remove('scrolled');
      }
    });
  }

  function createParticles() {
    const container = document.querySelector('.particles');
    if (!container) return;

    container.innerHTML = '';
    for (let i = 0; i < 30; i++) {
      const p = document.createElement('div');
      p.className = 'particle';
      p.style.left = (Math.random() * 100) + 'vw';
      p.style.top = (Math.random() * 100) + 'vh';
      p.style.animationDelay = (Math.random() * 10) + 's';
      p.style.animationDuration = (Math.random() * 8 + 12) + 's';
      container.appendChild(p);
    }
  }

  document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
      const href = this.getAttribute('href');
      if (!href || href === '#') return;
      e.preventDefault();
      const target = document.querySelector(href);
      if (target) {
        target.scrollIntoView({ behavior: 'smooth', block: 'start' });
      }
    });
  });

  document.addEventListener('DOMContentLoaded', function() {
    createParticles();

    if (!CSS.supports('view-timeline', '--test')) {
      const sections = document.querySelectorAll('.scroll-section');
      sections.forEach((section, index) => {
        section.style.animationDelay = (index * 0.2) + 's';
      });

      const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
          if (entry.isIntersecting) {
            entry.target.style.opacity = '1';
            entry.target.style.transform = 'scale(1) translateY(0)';
          }
        });
      }, { threshold: 0.3 });

      document.querySelectorAll('.scroll-section').forEach(section => {
        observer.observe(section);
      });
    }
  });
})();
