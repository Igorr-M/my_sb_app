<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="row g-3">
        <div class="col-md-5 mb-3">
            <form method="get" action="/main" class="row row-cols-lg-auto g-2 align-items-center">
                <div class="col-12">
                    <input type="text" name="filter" class="form-control" placeholder="Search by tag" value=${filter!}>
                </div>
                <div class="col-12">
                    <button type="submit" class="btn btn-primary">Search</button>
                </div>
            </form>
        </div>
    </div>

<#--    <a class="btn btn-primary my-3" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">-->
<#--        Add new message-->
<#--    </a>-->

    <#include "parts/messageEdit.ftlh"/>
    <#include "parts/messageList.ftlh"/>

</@c.page>