document.addEventListener('DOMContentLoaded', function() {
  const form = document.getElementById('loginForm');
  const message = document.getElementById('message');
  const cafesDiv = document.getElementById('cafes');
  const cafeList = document.getElementById('cafeList');

  // Si ya hay token, muestra los cafés directamente
  if (localStorage.getItem('token')) {
    form.style.display = 'none';
    loadCafes();
  }

  form.addEventListener('submit', async function(e) {
    e.preventDefault();
    message.textContent = '';
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    try {
      const res = await fetch('/api/users/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password })
      });
      if (!res.ok) throw new Error('Login incorrecto');
      const data = await res.json();
      localStorage.setItem('token', data.token);
      form.style.display = 'none';
      loadCafes();
    } catch (err) {
      message.textContent = err.message;
    }
  });

  window.logout = function() {
    localStorage.removeItem('token');
    cafesDiv.style.display = 'none';
    form.style.display = 'flex';
    message.textContent = 'Sesión cerrada';
  };

  async function loadCafes() {
    cafesDiv.style.display = 'block';
    cafeList.innerHTML = '<li>Cargando...</li>';
    try {
      const res = await fetch('/api/cafes', {
        headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') }
      });
      if (!res.ok) throw new Error('No autorizado o error al cargar cafés');
      const cafes = await res.json();
      cafeList.innerHTML = '';
      cafes.forEach(cafe => {
        const li = document.createElement('li');
        li.textContent = `${cafe.name} (${cafe.origin}) - $${cafe.price}`;
        cafeList.appendChild(li);
      });
    } catch (err) {
      cafeList.innerHTML = `<li>${err.message}</li>`;
    }
  }
}); 