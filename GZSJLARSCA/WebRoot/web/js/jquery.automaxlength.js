/**
 * Created by Bruce.Zhan on 2014/8/20.
 * 验证输入的最大字符
 */
(function ($) {

    $.fn.maxlen = function (args) {

        // default options
        var options = {
            regExp    : /[\u4e00-\u9fa5]/,
            maxLength : "maxlength",
            minLength : "minlength",
            isChinese : true
        };
        if (args) {
            $.extend(options, args);
        }

        return this.each(function () {
            var me = $(this);

            me.bind("keyup keydown blur", function () {
                if (me.val().match(options.regExp) != null) {
                    setVal(me);
                }
            });
        });

        // 设置值，如果输入超出最大范围那么截取超出部分
        function setVal(me) {
            // 存在minlength属性则禁用中文输入
            me.val(getVal(me.val(), isMinLength(me) ? 0 : me.attr(options.maxLength)));
        }

        // 获取值，中文截取最大范围的1/2
        function getVal(val, maxLen) {
            return val.substr(0, maxLen / 2);
        }

        // 是否存在minlength属性
        function isMinLength(me) {
            return me.attr(options.minLength) ? true : false;
        }

    };

    return this;

})(jQuery);