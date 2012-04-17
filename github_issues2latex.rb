require 'rubygems'
require 'httparty'
require 'json'


open = HTTParty.get('https://github.com/api/v2/json/issues/list/tomgi/what-to-eat/open')

puts '\begin{itemize}'
open['issues'].each do |issue|
  puts '  \item ' + issue['title']
end
puts '\end{itemize}'