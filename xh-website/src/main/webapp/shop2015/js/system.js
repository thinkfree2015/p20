$(function(){
    //首页
    (function(){
        //导航
        var navUl=$('.nav .ul-list');
        var navUlLi=navUl.find('li');
        var navUlLiActive=$('.nav .ul-list li.active');
        var navUlW=navUlLi.eq(0).height()*navUlLi.length;
        $('.btn-menu-down').click(function(){
            var ulList=$(this).siblings('.ul-list');

            $(this).hide().siblings('.btn-menu-up').show();
            ulList.stop(true).animate({'height':navUlW+'px'},200);
            $('.btn-menu-up').click(function(){
                $(this).hide().siblings('.btn-menu-down').show();
                ulList.stop(true).animate({'height':'40px'},200);
                return false;
            })
            return false;
        });
        //判断导航显示
        if($(document).width()<640){
            navUlLiActive.hide();
            navUlLi.eq(0).before('<li class="title">'+navUlLiActive.find('a').html()+'</li>')
        }
        //轮播图
        var ulNum=$('.slide-left .focus .am-slider-default .am-control-nav');
        if($(document).width()<970){
            ulNum.css({'margin-left':-ulNum.width()/2+'px'})
        }
        $(window).resize(function(){
            var ulNum=$('.slide-left .focus .am-slider-default .am-control-nav');
            if($(document).width()<970){
                ulNum.css({'margin-left':-ulNum.width()/2+'px','left':'50%'})
            }else{
                ulNum.css({'margin-left':'0','left':'450px'})
            }
        })
        if($(document).width()>640){
            //非遗名录
            var btnPrev=$('.home .directory .btn-prev');
            var btnNext=$('.home .directory .btn-next');
            var listImg=$('#list-img');
            var listImgUl=listImg.find('ul');
            var listImgLi=listImgUl.find('li');

            listImgUl.css({'width':listImgLi.length*181+'px'})
            var index=0;
            btnNext.click(function(){
                index++;
                if(index>listImgLi.length/2+1){
                    index=parseInt(listImgLi.length/2+1);
                }
                if(index>listImgLi.length/2){
                    $(this).hide();
                }
                $(this).siblings('.btn').show();
                listImgUl.stop(true,true).animate({'margin-left':-index*181+'px'});
                return false;
            });
            btnPrev.click(function(){
                index--;
                if(index<=0){
                    index=0;
                    $(this).hide();
                }
                $(this).siblings('.btn').show();
                listImgUl.stop(true,true).animate({'margin-left':-index*181+'px'});
                return false;
            });
        }


    })();
    //
    $('.org .slide-left .nav-list-ul li .btn-list').click(function(){
        $(this).parents('li').toggleClass('active');
        $(this).siblings('.items-list').stop(true).slideToggle('fast');
        return false
    })
    //X1010604申报查询
    var divList=$('#div-list');
    var divTabBox=divList.find('.div-tab-box');
    divTabBox.eq(0).show();
    divList.find('.div-tab-btn span').click(function(){
        var index=$(this).index();
        $(this).addClass('active').siblings('span').removeClass('active');
        divTabBox.eq(index).show().siblings('.div-tab-box').hide();
    })
})
