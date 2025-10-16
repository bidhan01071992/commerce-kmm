Pod::Spec.new do |spec|
    spec.name                     = 'shared'
    spec.version                  = '1.0.0'
    
    # Add these missing fields:
    spec.homepage                 = 'https://github.com/bidhan01071992/commerce-kmm'
    spec.source                   = { :git => 'https://github.com/bidhan01071992/commerce-kmm.git', :tag => "v#{spec.version}" }
    spec.authors                  = { 'Your Name' => 'your.email@example.com' }
    spec.license                  = { :type => 'MIT', :file => 'LICENSE' }
    spec.summary                  = 'KMM shared module for ecommerce functionality'
    spec.description              = 'Kotlin Multiplatform shared business logic for ecommerce application including networking, data models, and business rules'
    // Update the framework path
    spec.vendored_frameworks      = 'commerce-kmm/shared/KMMEcomProject/shared/build/cocoapods/framework/shared.framework'
    spec.libraries                = 'c++'
    spec.ios.deployment_target    = '13.0'
                
                
    if !Dir.exist?('build/cocoapods/framework/Shared.framework') || Dir.empty?('build/cocoapods/framework/Shared.framework')
        raise "

        Kotlin framework 'Shared' doesn't exist yet, so a proper Xcode project can't be generated.
        'pod install' should be executed after running ':generateDummyFramework' Gradle task:

            ./gradlew :shared:generateDummyFramework

        Alternatively, proper pod installation is performed during Gradle sync in the IDE (if Podfile location is set)"
    end
                
    spec.xcconfig = {
        'ENABLE_USER_SCRIPT_SANDBOXING' => 'NO',
    }
                
    spec.pod_target_xcconfig = {
        'KOTLIN_PROJECT_PATH' => ':shared',
        'PRODUCT_MODULE_NAME' => 'Shared',
    }
                
    spec.script_phases = [
    {
        :name => 'Build shared',
        :execution_position => :before_compile,
        :shell_path => '/bin/sh',
        :script => <<-SCRIPT
            if [ "YES" = "$OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED" ]; then
              echo "Skipping Gradle build task invocation"
              exit 0
            fi
            set -ev
            REPO_ROOT="$PODS_TARGET_SRCROOT"
            cd "$REPO_ROOT/shared/KMMEcomProject"
            ./gradlew :shared:syncFramework \
                -Pkotlin.native.cocoapods.platform=$PLATFORM_NAME \
                -Pkotlin.native.cocoapods.archs="$ARCHS" \
                -Pkotlin.native.cocoapods.configuration="$CONFIGURATION"
        SCRIPT
    }
]
                
end