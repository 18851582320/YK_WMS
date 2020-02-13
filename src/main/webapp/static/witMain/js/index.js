$(function() {
  var responsive = function() {
    var _height = $(window).height();
    var _top_height = $('.top').height();
    $('.content .content-main .cm-box, .content .navs-right').height(_height - _top_height - 25 - 20);
  };
  
  responsive();
  
  $(window).resize(responsive);
  
})
