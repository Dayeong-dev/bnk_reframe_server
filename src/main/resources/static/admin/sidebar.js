document.addEventListener("DOMContentLoaded", function () {
  const currentPath = window.location.pathname;

  // 모든 드롭다운 메뉴 링크 확인
  document.querySelectorAll(".dropdown a").forEach(subLink => {
    if (subLink.getAttribute("href") === currentPath) {
      // 부모 .sidebar-list 찾기
      const sidebarList = subLink.closest(".sidebar-list");
      if (sidebarList) {
        const topLink = sidebarList.querySelector(".sidebar-a");
        if (topLink) {
          topLink.classList.add("active");
        }
      }
    }
  });

  // 드롭다운이 없는 직접 메뉴 클릭 시도에도 대응
  document.querySelectorAll(".sidebar-a").forEach(link => {
    if (link.getAttribute("href") === currentPath) {
      link.classList.add("active");
    }
  });
});